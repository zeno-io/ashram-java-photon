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
