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
