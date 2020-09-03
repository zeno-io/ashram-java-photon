/*
 * MIT License
 *
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package xyz.flysium.photon;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.codehaus.groovy.control.CompilerConfiguration;
import xyz.flysium.photon.util.GroovyClassLoaderUtil;
import xyz.flysium.photon.util.GroovyScriptEngineJsr223Util;
import xyz.flysium.photon.util.GroovyScriptEngineUtil;
import xyz.flysium.photon.util.GroovyShellUtil;

/**
 * Test Java To Groovy GC.
 *
 * @author Sven Augustus
 */
public class Java2GroovyGcTest {

  private static final String S10 = "10";
  private static final String S11 = "11";
  private static final String S20 = "20";
  private static final String S21 = "21";
  private static final String S30 = "30";
  private static final String S31 = "31";
  private static final String S40 = "40";
  private static final String S41 = "41";

  private static String scriptText = "def mul(x, y) { x * y }\nprintln mul(5, 7)";
  private static String scriptFile = "cls3.groovy";

  public static void main(String[] args)
      throws IllegalAccessException, InterruptedException, InstantiationException, ResourceException, groovy.util.ScriptException, IOException, ScriptException {
    if (args.length == 0) {
      return;
    }
    // 操作系统：Manjaro 18.0.4 Illyria 内核：x86_64 Linux 4.19.32-1-MANJARO
    // CPU: Intel Core i7-8750H @ 12x 4.1GHz
    // RAM: 15897MiB
    // JDK : 1.8.0_212 , OpenJDK 64-Bit Server VM (build 25.212-b01, mixed mode)
    // Groovy : 2.5.6
    if (S10.equals(args[0])) {
      testGroovyShellNative();
      // -Xmx50m -verbose : 67222ms
      // Old Gen 持续增加，然后居高不下，其中 Old Gen gc 1202次，耗时 49s, GC 非常频繁
      // -Xmx250m -verbose : 26570ms
      // Old Gen 持续增加，其中 Old Gen gc 63次，耗时 7s, GC 相对频繁
    }
    if (S11.equals(args[0])) {
      testGroovyShellUtil();
      // -Xmx50m -verbose : 23265ms, Old Gen上下变动
      // -Xmx250m -verbose : 20363ms, Old Gen上下变动
    }
    if (S20.equals(args[0])) {
      testGroovyClassLoaderNative();
      // -Xmx50m -verbose : Old Gen一直持续增加，然后居高不下
      // Old Gen gc 1278次，耗时 45s, java.lang.OutOfMemoryError: GC overhead limit
      // exceeded
      // -Xmx250m -verbose : 31158ms
      // Old Gen一直持续增加，其中 Old Gen gc 64次，耗时 8s, GC 相对频繁
    }
    if (S21.equals(args[0])) {
      testGroovyClassLoaderUtil();
      // -Xmx50m -verbose : 1565ms
      // -Xmx250m -verbose : 1592ms
    }
    if (S30.equals(args[0])) {
      testGroovyScriptEngineNative();
      // -Xmx50m -verbose : 19723ms, 其中 Old Gen gc 86次，耗时 3s, GC 相对频繁
      // -Xmx250m -verbose : 20191ms, 其中 Old Gen gc 56次，耗时 3s, GC 相对频繁
    }
    if (S31.equals(args[0])) {
      testGroovyScriptEngineUtil();
      // -Xmx50m -verbose : 1835ms
      // -Xmx250m -verbose : 1750ms
    }
    if (S40.equals(args[0])) {
      testGroovyScriptEngineJsr223Native();
      // -Xmx50m -verbose : 23860ms, 其中 Old Gen gc 275次，耗时 7s, GC 非常频繁
      // -Xmx250m -verbose : 29405ms, 其中 Old Gen gc 113次，耗时 7s, GC 相对频繁
    }
    if (S41.equals(args[0])) {
      testGroovyScriptEngineJsr223Util();
      // -Xmx50m -verbose : 1552ms
      // -Xmx250m -verbose : 1576ms
    }
  }

  public static void testGroovyShellNative()
      throws InstantiationException, IllegalAccessException, InterruptedException {
    long start = System.currentTimeMillis();

    int times = 10000;
    for (int i = 0; i < times; i++) {
      GroovyShell shell = new GroovyShell();
      Script script = shell.parse(scriptText);
      script.run();
    }

    TimeUnit.SECONDS.sleep(1);
    System.gc();
    System.out.println((System.currentTimeMillis() - start) + "ms");
    // CPU还是有一定的使用率的，同时可以看到GC也不好，已装入的类快速上升，同时不管是堆还是Perm区的使用量都在快速增长，按照这个用法跑一会Perm区就要爆了。
    // 使用场景：这种直接插入groovy脚本的方式，还是适合比较简单的一些代码黏合吧，MWP这种调用次数较多，对性能有要求的场景明显不合适，跑会JVM就挂了。
  }

  public static void testGroovyShellUtil()
      throws InstantiationException, IllegalAccessException, InterruptedException {
    long start = System.currentTimeMillis();

    int times = 10000;
    for (int i = 0; i < times; i++) {
      GroovyShellUtil.eval(scriptText, null);
    }

    TimeUnit.SECONDS.sleep(1);
    System.gc();
    System.out.println((System.currentTimeMillis() - start) + "ms");
    // 增加了CompilerConfiguration配置，加速性能
  }

  /**
   * Error Usage
   * <p>
   * 1.所有的Groovy脚本采用一个全局的GroovyClassLoader，这种方式当应用用到了很多的Groovy Script时将出现问题， PermGen
   * 中的class能被卸载的前提是ClassLoader中所有的class都没有地方引用，因此如果是共用同一个GroovyClassLoader的话就很容易导致脚本class无法被卸载，从而导致
   * PermGen 被用满。
   * <p>
   * 2.每次都执行groovyLoader.parseClass(groovyScript)，Groovy 为了保证每次执行的都是新的脚本内容，在这里会每次生成一个新名字的 Class
   * 文件，代码如下： <code> return parseClass(text, "script" + System.currentTimeMillis() +
   * Math.abs(text.hashCode()) + ".groovy");
   * </code> 因此当对同一段脚本每次都执行这个方法时，会导致的现象就是装载的Class会越来越多，从而导致PermGen被用满。
   */
  public static void testGroovyClassLoaderNative()
      throws InstantiationException, IllegalAccessException, InterruptedException {
    long start = System.currentTimeMillis();

    CompilerConfiguration config = new CompilerConfiguration();
    config.setSourceEncoding("UTF-8");
    GroovyClassLoader classLoader = new GroovyClassLoader(
        Thread.currentThread().getContextClassLoader(), config);

    int times = 10000;
    for (int i = 0; i < times; i++) {
      Class groovyClass = classLoader.parseClass(scriptText);
      GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
      Script script = (Script) groovyObject;
      script.run();
    }
    TimeUnit.SECONDS.sleep(1);
    System.gc();
    System.out.println((System.currentTimeMillis() - start) + "ms");

    // 通常在使用 Groovy 装载和执行脚本时，比较好的做法是：
    // 1.每个 script 都 new 一个 GroovyClassLoader 来装载；
    // 2.对于 parseClass 后生成的 Class 对象进行cache，key 为 groovyScript 脚本的md5值。
    // 3.如果一个处理过程中要执行很多的Groovy Script（例如几百个）， 最好把这些Groovy Script合成一个或少数几个Script，
    // 这样一方面会节省PermGen的空间占用，另一方面也会一定程度的提升执行程序。
  }

  public static void testGroovyClassLoaderUtil()
      throws InstantiationException, IllegalAccessException, InterruptedException {
    long start = System.currentTimeMillis();

    int times = 10000;
    for (int i = 0; i < times; i++) {
      GroovyClassLoaderUtil.eval(scriptText, null);
    }
    TimeUnit.SECONDS.sleep(1);
    System.gc();
    System.out.println((System.currentTimeMillis() - start) + "ms");
  }

  public static void testGroovyScriptEngineNative() throws groovy.util.ScriptException, IOException,
      ResourceException, InterruptedException, IllegalAccessException, InstantiationException {
    long start = System.currentTimeMillis();
    String groovyPath = Thread.currentThread().getContextClassLoader().getResource("/groovy")
        .getPath();

    int times = 10000;
    for (int i = 0; i < times; i++) {
      CompilerConfiguration config = new CompilerConfiguration();
      config.setSourceEncoding(StandardCharsets.UTF_8.name());

      GroovyScriptEngine engine = new GroovyScriptEngine(groovyPath);
      engine.setConfig(config);
      Class groovyClass = engine.loadScriptByName(scriptFile);
      GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
      groovyObject.invokeMethod("mul", new Object[]{5, 7});
    }
    TimeUnit.SECONDS.sleep(1);
    System.gc();
    System.out.println((System.currentTimeMillis() - start) + "ms");
  }

  public static void testGroovyScriptEngineUtil()
      throws groovy.util.ScriptException, IOException, ResourceException,
      InterruptedException, IllegalAccessException, InstantiationException {
    long start = System.currentTimeMillis();

    int times = 10000;
    for (int i = 0; i < times; i++) {
      GroovyScriptEngineUtil.invokeFile(scriptFile, null, "mul", 5, 7);
    }
    TimeUnit.SECONDS.sleep(1);
    System.gc();
    System.out.println((System.currentTimeMillis() - start) + "ms");
  }

  public static void testGroovyScriptEngineJsr223Native()
      throws InstantiationException, IllegalAccessException, InterruptedException, ScriptException {
    long start = System.currentTimeMillis();

    int times = 10000;
    for (int i = 0; i < times; i++) {
      ScriptEngineManager factory = new ScriptEngineManager();
      ScriptEngine engine = factory.getEngineByName("groovy");
      engine.eval(scriptText);
    }
    TimeUnit.SECONDS.sleep(1);
    System.gc();
    System.out.println((System.currentTimeMillis() - start) + "ms");
  }

  public static void testGroovyScriptEngineJsr223Util()
      throws InstantiationException, IllegalAccessException, InterruptedException, ScriptException {
    long start = System.currentTimeMillis();

    int times = 10000;
    for (int i = 0; i < times; i++) {
      GroovyScriptEngineJsr223Util.eval(scriptText, null);
    }
    TimeUnit.SECONDS.sleep(1);
    System.gc();
    System.out.println((System.currentTimeMillis() - start) + "ms");
  }

}
