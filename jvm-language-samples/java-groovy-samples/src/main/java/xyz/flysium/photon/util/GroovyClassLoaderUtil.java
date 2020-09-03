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

package xyz.flysium.photon.util;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import javax.script.Bindings;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.runtime.IOGroovyMethods;

/**
 * <code>GroovyClassLoader</code> Util.
 * <link>http://docs.groovy-lang.org/latest/html/documentation/guide-integrating.html#groovyclassloader</link>
 *
 * @author Sven Augustus
 */
public class GroovyClassLoaderUtil {

  /**
   * <link>https://yq.aliyun.com/articles/2357?spm=5176.100239.yqblog1.6</link>
   * <p>
   * 通常在使用 Groovy 装载和执行脚本时，推荐方法：
   * <p>
   * 1.每个 script 都 new 一个 GroovyClassLoader 来装载；
   * </p>
   * 2.对于 parseClass 后生成的 Class 对象进行cache，key 为 groovyScript 脚本的md5值。
   * </p>
   * 3.如果一个处理过程中要执行很多的Groovy Script（例如几百个）， 最好把这些Groovy Script合成一个或少数几个Script，
   * 这样一方面会节省PermGen的空间占用，另一方面也会一定程度的提升执行程序。
   * </p>
   * 4.注意CodeCache的设置大小（来自：http://hellojava.info/） 对于大量使用Groovy的应用，尤其是Groovy脚本还会经常更新的应用，
   * 由于这些Groovy脚本在执行了很多次后都会被JVM编译为native进行优化，会占据一些CodeCache空间， 而如果这样的脚本很多的话，可能会导致CodeCache被用满，
   * 而CodeCache一旦被用满，JVM的Compiler就会被禁用，那性能下降的就不是一点点了。 CodeCache用满一方面是因为空间可能不够用，另一方面是Code
   * Cache是不会回收的， 所以会累积的越来越多（其实在不采用groovy这种动态更新/装载class的情况下的话，是不会太多的）， 所以解法一可以是增大code cache的size，
   * 可通过在启动参数上增加-XX:ReservedCodeCacheSize=256m（Oracle JVM Team那边也是推荐把code cache调大的）， 二是启用code
   * cache的回收机制（关于Code Cache flushing的具体策略请参见此文），可通过在启动参数上增加：-XX:+UseCodeCacheFlushing来启用。
   */
  private GroovyClassLoaderUtil() {
  }

  /**
   * Class Cache for Groovy Class Loader.
   */
  private static final GroovyClassCache CLASS_CACHE = new GroovyClassCache();

  @SuppressWarnings("unchecked")
  public static <T> Class<T> loadClass(String scriptText)
      throws InstantiationException, IllegalAccessException {
    String key = null;
    try {
      key = CLASS_CACHE.keyOf(scriptText);
    } catch (NoSuchAlgorithmException e) {
      // do nothing
    }
    Class groovyClass = null;
    if (key != null) {
      groovyClass = CLASS_CACHE.get(key);
    }
    if (groovyClass == null) {
      CompilerConfiguration config = new CompilerConfiguration();
      config.setSourceEncoding(StandardCharsets.UTF_8.name());

      GroovyClassLoader classLoader = new GroovyClassLoader(
          Thread.currentThread().getContextClassLoader(), config);
      groovyClass = classLoader.parseClass(scriptText);
      CLASS_CACHE.putIfAbsent(key, groovyClass);
    }

    return groovyClass;
  }

  public static <T> Class<T> loadClass(Reader reader)
      throws IOException, IllegalAccessException, InstantiationException {
    String scriptText = IOGroovyMethods.getText(reader);

    return loadClass(scriptText);
  }

  public static <T> Class<T> loadClassByFile(String scriptFileName)
      throws IOException, IllegalAccessException, InstantiationException {

    return loadClass(new FileReader(new File(GroovyUtil.getRoot(), scriptFileName)));
  }

  private static GroovyObject getGroovyObject(String scriptText)
      throws InstantiationException, IllegalAccessException {

    Class groovyClass = loadClass(scriptText);

    return (GroovyObject) groovyClass.newInstance();
  }

  public static Object eval(String scriptText, Bindings binding)
      throws IllegalAccessException, InstantiationException {
    GroovyObject groovyObject = getGroovyObject(scriptText);

    return GroovyUtil.eval(groovyObject, binding);
  }

  public static Object eval(Reader reader, Bindings binding)
      throws IllegalAccessException, InstantiationException, IOException {
    String scriptText = IOGroovyMethods.getText(reader);

    return eval(scriptText, binding);
  }

  public static Object invoke(String scriptText, Bindings binding, String name, Object... args)
      throws IllegalAccessException, InstantiationException {
    GroovyObject groovyObject = getGroovyObject(scriptText);

    return GroovyUtil.invoke(groovyObject, binding, name, args);
  }

  public static Object invoke(Reader reader, Bindings binding, String name, Object... args)
      throws IllegalAccessException, IOException, InstantiationException {
    String scriptText = IOGroovyMethods.getText(reader);

    return invoke(scriptText, binding, name, args);
  }

}
