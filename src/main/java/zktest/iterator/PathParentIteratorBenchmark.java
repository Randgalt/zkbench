package zktest.iterator;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

public class PathParentIteratorBenchmark
{
    private static final String[] paths = {
        "/a",
        "/a/b",
        "/a/b/c",
        "/a really long path",
        "/a really long path/with more than stuff",
        "/a really long path/with more than stuff/and more",
        "/a really long path/with more than stuff/and more/and more",
        "/a really long path/with more than stuff/and more/and more/and more"
    };

    @State(Scope.Benchmark)
    public static class Index {
        volatile int index = 0;
    }

    @Benchmark
    public void runOne(Index index)
    {
        PathParentIterator iterator = PathParentIterator.forAll(paths[index.index++]);
        if ( index.index >= paths.length ) {
            index.index = 0;
        }
        while ( iterator.hasNext() ) {
            iterator.next();
        }
    }
}
