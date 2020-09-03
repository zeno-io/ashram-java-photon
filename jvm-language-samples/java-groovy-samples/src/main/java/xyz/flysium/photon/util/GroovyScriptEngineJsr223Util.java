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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * ScriptEngine JSR 223 javax.script API.
 * <link>http://docs.groovy-lang.org/latest/html/documentation/guide-integrating.html#jsr223</link>
 *
 * @author Sven Augustus
 */
public class GroovyScriptEngineJsr223Util {

  private GroovyScriptEngineJsr223Util() {
  }

  /**
   * Global ScriptEngine.
   */
  private static ScriptEngine globalEngine;

  static {
    ScriptEngineManager factory = new ScriptEngineManager();
    globalEngine = factory.getEngineByName("groovy");
    // GroovyScriptEngineImpl
  }

  private static ScriptEngine getScriptEngine() {
    return globalEngine;
  }

  private static Object invoke(Invocable engine, Object n, Bindings binding, String name,
      Object... args)
      throws InstantiationException, IllegalAccessException, ScriptException, NoSuchMethodException {
    if (n instanceof Class) {
      GroovyObject groovyObject = (GroovyObject) ((Class) n).newInstance();

      GroovyUtil.setProperties(groovyObject, binding);

      return engine.invokeMethod(groovyObject, name, args);
    }

    return engine.invokeFunction(name, args);
  }

  public static Object eval(String scriptText, Bindings binding) throws ScriptException {
    ScriptEngine engine = getScriptEngine();

    return engine.eval(scriptText);
  }

  public static Object eval(Reader reader, Bindings binding) throws ScriptException {
    ScriptEngine engine = getScriptEngine();

    return engine.eval(reader);
  }

  public static Object invoke(String scriptText, Bindings binding, String name, Object... args)
      throws ScriptException, NoSuchMethodException, IllegalAccessException, InstantiationException {
    ScriptEngine engine = getScriptEngine();
    Object n = engine.eval(scriptText);

    return invoke((Invocable) engine, n, binding, name, args);
  }

  public static Object invoke(Reader reader, Bindings binding, String name, Object... args)
      throws ScriptException, NoSuchMethodException, IllegalAccessException, InstantiationException {
    ScriptEngine engine = getScriptEngine();
    Object n = eval(reader, binding);

    return invoke((Invocable) engine, n, binding, name, args);
  }

  public static Object invokeFile(String scriptFileName, Bindings binding, String name,
      Object... args)
      throws ScriptException, NoSuchMethodException, IllegalAccessException, InstantiationException, FileNotFoundException {
    ScriptEngine engine = getScriptEngine();
    Object n = eval(new FileReader(new File(GroovyUtil.getRoot(), scriptFileName)), binding);

    return invoke((Invocable) engine, n, binding, name, args);
  }

}
