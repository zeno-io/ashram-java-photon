
Java的基准测试需要注意的几个点
-  测试前需要代码预热。
-  执行时间的度量建议采用System.nanoTime，而不是System.currentTimeMillis。
-  不用把所有测试方法写到main方法中，避免某些高级JVM采用堆栈上替换（OSR）。
-  不要在测试中使用循环。要测试真实的计算，让JMH处理剩余的部分。
-  防止无用代码进入测试方法中。
-  多次测试并统计。
-  测试方法建议返回值形式，避免JVM过度优化，导致结果失真。

# JMH
JMH：http://openjdk.java.net/projects/code-tools/jmh/

Java使用JMH进行简单的基准测试Benchmark

这里说道的基准测试Benchmark其实是微基准测试Micro-Benchmark。

详细的概念可以参见：https://github.com/google/caliper/wiki/JavaMicrobenchmarks

健壮的Java基准测试
https://www.ibm.com/developerworks/cn/java/j-benchmark1.html
https://www.ibm.com/developerworks/cn/java/j-benchmark2/index.html
我们可以看到影响基准测试的因素比较多，包括代码预热、编译器动态优化、资源回收（GC）、文件缓存、电源、其他程序，JVM的VM选项等等。我们在进行基准测试前需要关注其对产生结果的影响。

```xml
<!-- https://mvnrepository.com/artifact/org.openjdk.jmh/jmh-core -->
<dependency>
    <groupId>org.openjdk.jmh</groupId>
    <artifactId>jmh-core</artifactId>
    <version>1.20</version>
</dependency>
 <dependency>
     <groupId>org.openjdk.jmh</groupId>
     <artifactId>jmh-generator-annprocess</artifactId>
     <version>1.20</version>
</dependency>
```


JMH官方例子：

http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/


## 实例
```java
@BenchmarkMode(Mode.AverageTime) //基准测试类型
@Warmup(iterations = 3) //预热的迭代次数
@Measurement(iterations = 3)    // 度量:iterations进行测试的轮次，time每轮进行的时长，timeUnit时长单位,batchSize批次数量
@Threads(6) //测试线程数量
@Fork(2) // 测试进程数量
@OutputTimeUnit(TimeUnit.MILLISECONDS) //基准测试结果的时间类型
@State(Scope.Benchmark)
public class StringBufferBuilderTests {

  @Benchmark
  public String testString() {
    String buf = "";
    for (int i = 0; i < 10000; i++) {
      buf += i;
    }
    return buf;
  }

  @Benchmark
  public String testStringBuffer() {
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < 10000; i++) {
      buf.append(i);
    }
    return buf.toString();
  }

  @Benchmark
  public String testStringBuilder() {
    StringBuilder buf = new StringBuilder();
    for (int i = 0; i < 10000; i++) {
      buf.append(i);
    }
    return buf.toString();
  }
}
```

>测试：
如果是 `IDEA`， 可以安装 `JHM Plugin`, 测试就直接 `Run` (注意不支持 `Debug`)。

## 基本概念
- `Warmup` 预热，由于JVM中对于特定代码会存在优化（本地化），预热对于测试结果很重要
- `Mesurement`  总共执行多少次测试
- `Timeout` 超时时间
- `Threads` 线程数，由fork指定
- `Benchmark mode` 基准测试的模式
- `Benchmark`   测试哪一段代码
   
# 类注解
## @BenchmarkMode 测试模式
测试方法上`@BenchmarkMode`注解表示使用特定的测试模式：

| 名称 | 描述 |
| --- | ---  |
| Mode.Throughput	|计算一个时间单位内操作数量 |
| Mode.AverageTime	|计算平均运行时间 |
| Mode.SampleTime	|计算一个方法的运行时间(包括百分位) |
| Mode.SingleShotTime	| 方法仅运行一次(用于冷测试模式)。或者特定批量大小的迭代多次运行<br>(具体查看后面的`“@Measurement“`注解)——<br>这种情况下JMH将计算批处理运行时间(一次批处理所有调用的总时间) |
| 这些模式的任意组合| 可以指定这些模式的任意组合——该测试运行多次(取决于请求模式的数量) |
| Mode.All	| 所有模式依次运行 |


## @Warmup 预热

进行基准测试前需要进行预热。一般我们前几次进行程序测试的时候都会比较慢， 所以要让程序进行几轮预热，保证测试的准确性。其中的参数 `iterations` 也就非常好理解了，就是预热轮数。

为什么需要预热？

因为 JVM 的 `JIT` 机制的存在，如果某个函数被调用多次之后，JVM 会尝试将其编译成为机器码从而提高执行速度。所以为了让 benchmark 的结果更加接近真实情况就需要进行预热。
- `iterations`：预热的次数。
- `time`：每次预热的时间。
- `timeUnit`：时间的单位，默认秒。
- `batchSize`：批处理大小，每次操作调用几次方法。

## @Measurement 度量
`@Measurement` 度量，其实就是一些基本的测试参数。
- `iterations` 进行测试的轮次
-  `time` 每轮进行的时长
-  `timeUnit` 时长单位

都是一些基本的参数，可以根据具体情况调整。一般比较重的东西可以进行大量的测试，放到服务器上运行。

##  @Threads 测试线程数

每个进程中的测试线程，这个非常好理解，根据具体情况选择，一般为cpu乘以2。

如果配置了 `Threads.MAX` ，代表使用 `Runtime.getRuntime().availableProcessors()` 个线程。

## @Fork

进行 fork 的次数。可用于类或者方法上。如果 fork 数是2的话，则 JMH 会 fork 出两个进程来进行测试。

## @OutputTimeUnit 时间单位
使用`@OutputTimeUnit`指定时间单位，它需要一个标准Java类型`java.util.concurrent.TimeUnit`作为参数。

可是如果在一个测试中指定了多种测试模式，给定的时间单位将用于所有的测试(比如，测试`SampleTime`适宜使用纳秒，但是`throughput`使用更长的时间单位测量更合适)。

## @State 测试参数状态

测试方法可能接收参数。这需要提供单个的参数类，这个类遵循以下4条规则：

- 有无参构造函数(默认构造函数)
- 是公共类
- 内部类应该是静态的
- 该类必须使用@State注解

`@State`注解定义了给定类实例的可用范围。JMH可以在多线程同时运行的环境测试，因此需要选择正确的状态。

| 名称 | 描述 |
| --- | --- |
| Scope.Thread | 默认状态。实例将分配给运行给定测试的每个线程。|
| Scope.Benchmark	| 运行相同测试的所有线程将共享实例。可以用来测试状态对象的多线程性能(或者仅标记该范围的基准)。|
| Scope.Group	| 实例分配给每个线程组 |

除了将单独的类标记`@State`，也可以将你自己的benchmark类使用`@State`标记。上面所有的规则对这种情况也适用。

>当使用`@Setup` 或`@TearDown` 参数的时候，必须在类上加这个参数，不然会提示无法运行。


# 方法注解
## @Benchmark
`@Benchmark`，表示该方法是需要进行 benchmark 的对象，用法和 JUnit 的 `@Test` 类似。

## @CompilerControl 编译器提示
可以为JIT提供关于如何使用测试程序中任何方法的提示。“任何方法”是指任何的方法——不仅仅是`@GenerateMicroBenchmark`注解的方法。使用`@CompilerControl`模式(还有更多模式，但是我不确定它们的有用程度)：

| 名称 | 描述|
| --- | ---|
| CompilerControl.Mode.DONT_INLINE	| 禁止使用内联。<br>用于测量方法调用开销和评估是否该增加JVM的inline阈值 |
| CompilerControl.Mode.INLINE	| 强制使用内联。<br>通常与“`Mode.DONT_INLINE“`联合使用，检查内嵌的利弊。 |
| CompilerControl.Mode.EXCLUDE	| 禁止编译方法 |

## @Setup 与 @TearDown
`@Setup`主要实现测试前的初始化工作，只能作用在方法上。用法和Junit一样。使用该注解必须定义 `@State`注解。

`@TearDown`主要实现测试完成后的垃圾回收等工作，只能作用在方法上。用法和Junit一样。使用该注解必须定义 `@State` 注解。

这两个注解都有一个 `Level` 的枚举value，它有三个值（默认的是Trial）：
- `Trial`：在每次Benchmark的之前/之后执行。
- `Iteration`：在每次Benchmark的iteration的之前/之后执行。
- `Invocation`：每次调用Benchmark标记的方法之前/之后都会执行。

可见，`Level`的粒度从`Trial`到`Invocation`越来越细。

## @Group
结合`@Benchmark`一起使用，把多个基准方法归为一类。同一个组中的所有测试设置相同的名称(否则这些测试将独立运行——没有任何警告提示！)

## @GroupThreads
定义了多少个线程参与在组中运行基准方法。

# 属性注解
## @Param
`@Param` 可以用来指定某项参数的多种情况。特别适合用来测试一个函数在不同的参数输入的情况下的性能。

#  避免JIT优化
- 基准测试方法一定不要返回void。
- 如果要使用void返回，可以使用 `Blackhole` 的 `consume` 来避免JIT的优化消除。
- 计算不要引用常量，否则会被优化到JMH的循环之外。

```java
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3) 
@Measurement(iterations = 3)  
@Threads(6)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class Tests {

    private double x = Math.PI;
    // private final double x = Math.PI;

    @Benchmark
    public void baseline() {
        // do nothing, this is a baseline
    }
    
    @Benchmark
    public void measureWrong() {
        // This is wrong: result is not used and the entire computation is optimized away.
        Math.log(x);
    }
    
    @Benchmark
    public double measureRight() {
        // This is correct: the result is being used.
        return Math.log(x);
    }

    @Benchmark
    public void measureRight_2(Blackhole bh) {
        bh.consume(Math.log(x));
    }

}
```