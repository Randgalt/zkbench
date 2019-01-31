# ZooKeeper Benchmark Tests

Wrapper around `jmh` for benchmarking Apache ZooKeeper/Curator tests.

## Building

```
> git clone https://git.com/Randgalt/zkbench.git
> cd zkbench
> mvn clean package
```

## Running

*Get Help*

```
> java -jar target/benchmarks.jar -h
```

Run all benchmarks

```
> java -jar target/benchmarks.jar
```

List all benchmarks

```
> java -jar target/benchmarks.jar -l
```

Run a single benchmark

```
> java -jar target/benchmarks.jar <benchmark name>
```

## Latest Result

```
Benchmark                                       Mode  Cnt         Score        Error  Units
z.cache.curator.CuratorCachingBenchmark.run    thrpt  200      2072.089 ±     67.370  ops/s
z.cache.tree.TreeCachingBenchmark.run          thrpt  200       851.644 ±     13.374  ops/s
z.iterator.PathParentIteratorBenchmark.runOne  thrpt  200  24321939.274 ± 282185.451  ops/s
```
