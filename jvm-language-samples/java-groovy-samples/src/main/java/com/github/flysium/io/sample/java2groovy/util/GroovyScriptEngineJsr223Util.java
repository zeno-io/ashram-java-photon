/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.sample.java2groovy.util;

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
