package zktest.cache;

import java.io.Closeable;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public interface CacheWorker extends Closeable
{
    void start(List<String> paths, Map<String, Boolean> nodeMap);

    void wrapOperation(Supplier<String> pathProc);
}
