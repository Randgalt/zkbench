package zktest.cache.curator;

import org.openjdk.jmh.annotations.Benchmark;
import zktest.cache.CachingBenchmarkBase;

public class CuratorCachingBenchmark extends CachingBenchmarkBase
{
    @Benchmark
    public void run(CuratorCacheCachingState state)
    {
        runStandard(state);
    }
}
