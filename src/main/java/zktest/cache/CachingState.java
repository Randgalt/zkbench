package zktest.cache;

import com.google.common.base.MoreObjects;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.utils.ZKPaths;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@State(Scope.Benchmark)
public abstract class CachingState implements Closeable
{
    private final TestingServer server;
    private final CuratorFramework client;
    private final Map<String, Boolean> nodeMap = new ConcurrentHashMap<>();
    private final List<String> paths;
    private final CacheWorker cache;

    private static final int NODE_QTY = 10000;
    private static final int MAX_PATHS = 5;
    private static final String NODE_NAME = "this-is-a-node";

    public static final String PARENT_PATH = "/zktest";

    public CachingState()
    {
        try
        {
            this.server = new TestingServer();
        }
        catch ( Exception e )
        {
            throw new RuntimeException(e);
        }

        client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(100, 3));
        cache = makeCache(client, PARENT_PATH);

        int nodeName = 0;
        List<String> workerNodeNames = new ArrayList<>();
        for ( int i = 0; i < NODE_QTY; ++i )
        {
            int pathQty = ThreadLocalRandom.current().nextInt(MAX_PATHS) + 1;
            String path = PARENT_PATH;
            while ( pathQty-- >= 0 )
            {
                path = ZKPaths.makePath(path, NODE_NAME);
            }
            path = ZKPaths.makePath(path, "node-" + nodeName++);
            nodeMap.put(path, false);
            workerNodeNames.add(path);
        }
        paths = Collections.unmodifiableList(workerNodeNames);
    }

    protected abstract CacheWorker makeCache(CuratorFramework client, String parentPath);

    public CacheWorker cache()
    {
        return cache;
    }

    public CuratorFramework getClient()
    {
        return client;
    }

    @Setup
    public void start()
    {
        client.start();
        try
        {
            cache.start(paths, nodeMap);
        }
        catch ( Exception e )
        {
            throw new RuntimeException(e);
        }
    }

    @TearDown
    @Override
    public void close()
    {
        CloseableUtils.closeQuietly(cache);
        CloseableUtils.closeQuietly(client);
        CloseableUtils.closeQuietly(server);
    }

    public String performOperation()
    {
        String path = paths.get(ThreadLocalRandom.current().nextInt(paths.size()));
        boolean exists = MoreObjects.firstNonNull(nodeMap.get(path), Boolean.FALSE);
        try
        {
            if ( exists )
            {
                if ( ThreadLocalRandom.current().nextBoolean() )
                {
                    client.delete().forPath(path);
                    nodeMap.put(path, false);
                }
                else
                {
                    client.setData().forPath(path, "afjasfjasf".getBytes());
                }
            }
            else
            {
                client.create().creatingParentsIfNeeded().forPath(path);
                nodeMap.put(path, true);
            }
        }
        catch ( Exception e )
        {
            throw new RuntimeException(e);
        }
        return path;
    }

    public static void addInitialNodes(CuratorFramework client, List<String> paths, Map<String, Boolean> nodeMap)
    {
        paths.forEach(path -> {
            try
            {
                client.create().creatingParentsIfNeeded().forPath(path);
                nodeMap.put(path, true);
            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }
        });
    }
}
