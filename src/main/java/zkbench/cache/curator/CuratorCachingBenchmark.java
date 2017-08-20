package zkbench.cache.curator;

import org.openjdk.jmh.annotations.Benchmark;
import zkbench.cache.CachingBenchmarkBase;

public class CuratorCachingBenchmark extends CachingBenchmarkBase
{
    @Benchmark
    public void run(CuratorCacheCachingState state)
    {
        runStandard(state);
    }
}
