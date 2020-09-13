package xyz.flysium.photon;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

/**
 * TODO description
 *
 * @author zeno
 */
@BenchmarkMode(Mode.AverageTime) //基准测试类型
@Warmup(iterations = 3) //预热的迭代次数
@Measurement(iterations = 3)    // 度量:iterations进行测试的轮次，time每轮进行的时长，timeUnit时长单位,batchSize批次数量
@Threads(6) // 测试线程数量
@Fork(2) // 测试进程数量
@OutputTimeUnit(TimeUnit.NANOSECONDS) //基准测试结果的时间类型
@State(Scope.Thread)
public class ReflectMethodHandlesTests {

    private int value = 42;

    private static final Field static_reflective;
    private static final MethodHandle static_unreflect;
    private static final MethodHandle static_mh;

    private static Field reflective;
    private static MethodHandle unreflect;
    private static MethodHandle mh;

    // We would normally use @Setup, but we need to initialize "static final" fields here...
    static {
        try {
            reflective = ReflectMethodHandlesTests.class.getDeclaredField("value");
            unreflect = MethodHandles.lookup().unreflectGetter(reflective);
            mh = MethodHandles.lookup()
                .findGetter(ReflectMethodHandlesTests.class, "value", int.class);
            static_reflective = reflective;
            static_unreflect = unreflect;
            static_mh = mh;
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new IllegalStateException(e);
        }
    }

    @Benchmark
    public int plain() {
        return value;
    }

    @Benchmark
    public int dynamic_reflect() throws InvocationTargetException, IllegalAccessException {
        return (int) reflective.get(this);
    }

    @Benchmark
    public int dynamic_unreflect_invoke() throws Throwable {
        return (int) unreflect.invoke(this);
    }

    @Benchmark
    public int dynamic_unreflect_invokeExact() throws Throwable {
        return (int) unreflect.invokeExact(this);
    }

    @Benchmark
    public int dynamic_mh_invoke() throws Throwable {
        return (int) mh.invoke(this);
    }

    @Benchmark
    public int dynamic_mh_invokeExact() throws Throwable {
        return (int) mh.invokeExact(this);
    }

    @Benchmark
    public int static_reflect() throws InvocationTargetException, IllegalAccessException {
        return (int) static_reflective.get(this);
    }

    @Benchmark
    public int static_unreflect_invoke() throws Throwable {
        return (int) static_unreflect.invoke(this);
    }

    @Benchmark
    public int static_unreflect_invokeExact() throws Throwable {
        return (int) static_unreflect.invokeExact(this);
    }

    @Benchmark
    public int static_mh_invoke() throws Throwable {
        return (int) static_mh.invoke(this);
    }

    @Benchmark
    public int static_mh_invokeExact() throws Throwable {
        return (int) static_mh.invokeExact(this);
    }

//# Run complete. Total time: 00:02:17
//
//Benchmark                                                Mode  Cnt  Score   Error  Units
//ReflectMethodHandlesTests.dynamic_mh_invoke              avgt    6  5.815 ± 0.272  ns/op
//ReflectMethodHandlesTests.dynamic_mh_invokeExact         avgt    6  5.651 ± 0.198  ns/op
//ReflectMethodHandlesTests.dynamic_reflect                avgt    6  6.234 ± 1.643  ns/op
//ReflectMethodHandlesTests.dynamic_unreflect_invoke       avgt    6  6.606 ± 2.469  ns/op
//ReflectMethodHandlesTests.dynamic_unreflect_invokeExact  avgt    6  5.889 ± 1.095  ns/op
//ReflectMethodHandlesTests.plain                          avgt    6  2.948 ± 1.244  ns/op
//ReflectMethodHandlesTests.static_mh_invoke               avgt    6  3.360 ± 3.421  ns/op
//ReflectMethodHandlesTests.static_mh_invokeExact          avgt    6  2.547 ± 0.115  ns/op
//ReflectMethodHandlesTests.static_reflect                 avgt    6  6.803 ± 0.899  ns/op
//ReflectMethodHandlesTests.static_unreflect_invoke        avgt    6  2.914 ± 1.164  ns/op
//ReflectMethodHandlesTests.static_unreflect_invokeExact   avgt    6  2.622 ± 0.690  ns/op

}
