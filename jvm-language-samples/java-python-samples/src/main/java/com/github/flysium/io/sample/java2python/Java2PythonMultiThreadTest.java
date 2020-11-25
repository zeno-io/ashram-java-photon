package com.github.flysium.io.sample.java2python;

import com.github.flysium.io.sample.java2python.util.JythonUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Test Java To Groovy In multiThread environment.
 *
 * @author Sven Augustus
 */
public class Java2PythonMultiThreadTest {

  private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(8, 32, 60,
    TimeUnit.SECONDS,
    new SynchronousQueue<>(), Executors.defaultThreadFactory(),
    new ThreadPoolExecutor.CallerRunsPolicy());

  private static final int INT = 3;

  private static final int TIMES = 100;

  public static void main(String[] args) throws InterruptedException {
    long start = System.currentTimeMillis();

    String scriptText =
      // @formatter:off
      "def mul(x, y):\n"
        + "  return x * y \n"
        + "result = mul(a, b)\n";
    // @formatter:on

    List<Future<CalcResult>> futures = new ArrayList<>(TIMES);
    for (int i = 0; i < TIMES; i++) {
      if (i % INT == 0) {
        futures.add(THREAD_POOL_EXECUTOR.submit(new Callable<CalcResult>() {

          @Override
          public CalcResult call() throws Exception {
            Map<String, Object> arguments = new HashMap<>(8);
            arguments.put("a", 3);
            arguments.put("b", 5);
            return new CalcResult(3, 5,
              JythonUtil.computeToJavaResult(scriptText, arguments, "result", Integer.class));
          }
        }));
      }
      if (i % INT == 1) {
        futures.add(THREAD_POOL_EXECUTOR.submit(new Callable<CalcResult>() {

          @Override
          public CalcResult call() throws Exception {
            Map<String, Object> arguments = new HashMap<>(8);
            arguments.put("a", 2);
            arguments.put("b", 6);
            return new CalcResult(2, 6,
              JythonUtil.computeToJavaResult(scriptText, arguments, "result", Integer.class));
          }
        }));
      }
    }
    THREAD_POOL_EXECUTOR.shutdown();
    for (Future<CalcResult> future : futures) {
      CalcResult result = null;
      try {
        result = future.get();
        if (result.a * result.b != result.result) {
          System.out.println("Error test !, result = " + result);
        }
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
    }
    System.gc();
    System.out.println((System.currentTimeMillis() - start) + "ms");
  }

  private static class CalcResult {

    private int a;

    private int b;

    private int result;

    public CalcResult(int a, int b, int result) {
      this.a = a;
      this.b = b;
      this.result = result;
    }

    @Override
    public String toString() {
      return "CalcResult{" + "a=" + a + ", b=" + b + ", result=" + result + '}';
    }
  }

}
