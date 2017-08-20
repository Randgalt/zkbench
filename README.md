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

