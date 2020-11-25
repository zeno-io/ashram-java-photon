package com.github.flysium.io.sample.java2python;

import com.github.flysium.io.sample.java2python.util.JythonUtil;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

/**
 * Test Java To Jython GC.
 *
 * @author Sven Augustus
 */
public class Java2PythonGcTest {

  private static final String S1 = "1";

  private static final String S2 = "2";

  private static final int TIMES = 10000;

  private static String scriptText =
    // @formatter:off
    "def mul(x, y):\n"
      + "  return x * y \n"
      + "result = mul(5, 7)\n";
  // @formatter:on

  public static void main(String[] args) throws InterruptedException {
    // 操作系统：Manjaro 18.0.4 Illyria 内核：x86_64 Linux 4.19.32-1-MANJARO
    // CPU: Intel Core i7-8750H @ 12x 4.1GHz
    // RAM: 15897MiB
    // JDK : 1.8.0_212 , OpenJDK 64-Bit Server VM (build 25.212-b01, mixed mode)
    // Jython : 2.7.1
    // 注意这里默认都加了1秒睡眠
    if (args.length == 0) {
      return;
    }
    if (S1.equals(args[0])) {
      testJythonNative();
      // -Xmx50m : 7689ms
      // -Xmx250m : 7386ms
    }
    if (S2.equals(args[0])) {
      testJythonUtil();
      // -Xmx50m : 3113ms
      // -Xmx250m : 2883ms
    }
  }

  /**
   * Error Usage
   */
  private static void testJythonNative() throws InterruptedException {
    long start = System.currentTimeMillis();

    PySystemState.initialize();

    PySystemState pySystemState = new PySystemState();
    pySystemState.setClassLoader(Thread.currentThread().getContextClassLoader());
    pySystemState.setdefaultencoding(StandardCharsets.UTF_8.name());

    for (int i = 0; i < TIMES; i++) {
      PythonInterpreter interpreter = new PythonInterpreter(null, pySystemState);
      // 执行 Python 脚本
      interpreter.exec(scriptText);
    }
    TimeUnit.SECONDS.sleep(1);
    System.gc();
    System.out.println(("" + (System.currentTimeMillis() - start)) + "ms");
  }

  private static void testJythonUtil() throws InterruptedException {
    long start = System.currentTimeMillis();

    for (int i = 0; i < TIMES; i++) {
      JythonUtil.exec(scriptText);
    }
    TimeUnit.SECONDS.sleep(1);
    System.gc();
    System.out.println(("" + (System.currentTimeMillis() - start)) + "ms");
  }

}
