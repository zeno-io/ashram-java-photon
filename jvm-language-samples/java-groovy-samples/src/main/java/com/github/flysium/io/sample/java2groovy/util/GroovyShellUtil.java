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

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import javax.script.Bindings;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.runtime.IOGroovyMethods;

/**
 * <code>GroovyShell</code> Util.
 * <link>http://docs.groovy-lang.org/latest/html/documentation/guide-integrating.html#integ-groovyshell</link>
 *
 * @author Sven Augustus
 */
public class GroovyShellUtil {

  private GroovyShellUtil() {
  }

  private static Script getScript(String scriptText, Bindings binding) {
    CompilerConfiguration config = new CompilerConfiguration();
    config.setSourceEncoding(StandardCharsets.UTF_8.name());

    GroovyShell shell = new GroovyShell(config);
    Script script = shell.parse(scriptText);
    if (binding != null) {
      script.setBinding(new Binding(binding));
    }
    return script;
  }

  public static Object eval(String scriptText, Bindings binding)
      throws IllegalAccessException, InstantiationException {
    Script script = getScript(scriptText, binding);

    return script.run();
  }

  public static Object eval(Reader reader, Bindings binding)
      throws IllegalAccessException, InstantiationException, IOException {
    String scriptText = IOGroovyMethods.getText(reader);
    return eval(scriptText, binding);
  }

  public static Object invoke(String scriptText, Bindings binding, String name, Object... args)
      throws IllegalAccessException, InstantiationException {
    Script script = getScript(scriptText, binding);

    return script.invokeMethod(name, args);
  }

  public static Object invoke(Reader reader, Bindings binding, String name, Object... args)
      throws IllegalAccessException, IOException, InstantiationException {
    String scriptText = IOGroovyMethods.getText(reader);
    return invoke(scriptText, binding, name, args);
  }

}
