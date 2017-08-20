package zkbench.cache.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.watch.CacheEvent;
import org.apache.curator.framework.recipes.watch.CacheListener;
import org.apache.curator.framework.recipes.watch.CuratorCache;
import org.apache.curator.framework.recipes.watch.CuratorCacheBuilder;
import zkbench.cache.CacheWorker;
import zkbench.cache.CachingState;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

public class CuratorCacheCachingState extends CachingState
{
    @Override
    protected CacheWorker makeCache(CuratorFramework client, String parentPath)
    {
        CuratorCache cache = CuratorCacheBuilder.builder(client, parentPath).build();
        return new CacheWorker()
        {
            @Override
            public void start(List<String> paths, Map<String, Boolean> nodeMap)
            {
                CachingState.addInitialNodes(client, paths, nodeMap);

                CountDownLatch latch = new CountDownLatch(1);
                CacheListener t = (cacheEvent, s, cachedNode) -> {
                    if ( cacheEvent == CacheEvent.CACHE_REFRESHED ) {
                        latch.countDown();
                    }
                };
                cache.getListenable().addListener(t);
                try
                {
                    cache.start();
                    latch.await();
                }
                catch ( Exception e )
                {
                    throw new RuntimeException(e);
                }
                finally
                {
                    cache.getListenable().removeListener(t);
                }
            }

            @Override
            public void wrapOperation(Supplier<String> pathProc)
            {
                BlockingQueue<String> queue = new LinkedBlockingQueue<>();
                CacheListener listener = (cacheEvent, p, cachedNode) -> queue.add(p);
                cache.getListenable().addListener(listener);
                String path = pathProc.get();
                try
                {
                    String takenPath = queue.take();
                    if ( !takenPath.equals(path) )
                    {
                        throw new RuntimeException("Incorrect path");
                    }
                }
                catch ( InterruptedException e )
                {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupted");
                }
                finally
                {
                    cache.getListenable().removeListener(listener);
                }
            }

            @Override
            public void close() throws IOException
            {
                cache.close();
            }
        };
    }
}
