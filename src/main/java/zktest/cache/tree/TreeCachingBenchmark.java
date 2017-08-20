package zktest.cache.tree;

import org.openjdk.jmh.annotations.Benchmark;
import zktest.cache.CachingBenchmarkBase;

public class TreeCachingBenchmark extends CachingBenchmarkBase
{
    @Benchmark
    public void run(TreeCacheCachingState state)
    {
        runStandard(state);
    }
}
