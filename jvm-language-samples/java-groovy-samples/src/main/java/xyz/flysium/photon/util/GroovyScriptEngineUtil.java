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

import groovy.lang.GroovyObject;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.script.Bindings;
import org.codehaus.groovy.control.CompilerConfiguration;

/**
 * <code>GroovyScriptEngine</code> Util.
 * <link>http://docs.groovy-lang.org/latest/html/documentation/guide-integrating.html#_groovyscriptengine</link>
 *
 * @author Sven Augustus
 */
public class GroovyScriptEngineUtil {

  private GroovyScriptEngineUtil() {
  }

  /**
   * Global ScriptEngine.
   */
  private static GroovyScriptEngine globalEngine;

  static {
    CompilerConfiguration config = new CompilerConfiguration();
    config.setSourceEncoding(StandardCharsets.UTF_8.name());
    try {
      globalEngine = new GroovyScriptEngine(GroovyUtil.getRoots());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> Class<T> loadClassByFile(String scriptFileName)
      throws InstantiationException, IllegalAccessException, ResourceException, ScriptException {
    CompilerConfiguration config = new CompilerConfiguration();
    config.setSourceEncoding(StandardCharsets.UTF_8.name());

    return globalEngine.loadScriptByName(scriptFileName);
  }

  private static GroovyObject getGroovyObject(String scriptFileName)
      throws InstantiationException, IllegalAccessException, IOException, ResourceException, ScriptException {
    Class groovyClass = loadClassByFile(scriptFileName);

    return (GroovyObject) groovyClass.newInstance();
  }

  public static Object evalFile(String scriptFileName, Bindings binding)
      throws IllegalAccessException, InstantiationException, ResourceException, ScriptException, IOException {
    GroovyObject groovyObject = getGroovyObject(scriptFileName);

    return GroovyUtil.eval(groovyObject, binding);
  }

  public static Object invokeFile(String scriptFileName, Bindings binding, String name,
      Object... args)
      throws IOException, ScriptException, InstantiationException, ResourceException, IllegalAccessException {
    GroovyObject groovyObject = getGroovyObject(scriptFileName);

    return GroovyUtil.invoke(groovyObject, binding, name, args);
  }

}
