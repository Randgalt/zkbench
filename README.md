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

Result "zkbench.cache.curator.CuratorCachingBenchmark.run":
  2072.089 ±(99.9%) 67.370 ops/s [Average]
  (min, avg, max) = (98.767, 2072.089, 2657.587), stdev = 285.249
  CI (99.9%): [2004.719, 2139.459] (assumes normal distribution)

Result "zkbench.cache.tree.TreeCachingBenchmark.run":
  851.644 ±(99.9%) 13.374 ops/s [Average]
  (min, avg, max) = (587.884, 851.644, 955.431), stdev = 56.627
  CI (99.9%): [838.269, 865.018] (assumes normal distribution)

Result "zkbench.iterator.PathParentIteratorBenchmark.runOne":
  24321939.274 ±(99.9%) 282185.451 ops/s [Average]
  (min, avg, max) = (20263468.579, 24321939.274, 26600513.627), stdev = 1194790.868
  CI (99.9%): [24039753.823, 24604124.725] (assumes normal distribution)
```
