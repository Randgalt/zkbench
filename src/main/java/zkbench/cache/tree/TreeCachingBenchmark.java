package zkbench.cache.tree;

import org.openjdk.jmh.annotations.Benchmark;
import zkbench.cache.CachingBenchmarkBase;

public class TreeCachingBenchmark extends CachingBenchmarkBase
{
    @Benchmark
    public void run(TreeCacheCachingState state)
    {
        runStandard(state);
    }
}
