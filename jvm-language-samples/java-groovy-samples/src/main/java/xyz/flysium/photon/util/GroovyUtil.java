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
import groovy.lang.Script;
import javax.script.Bindings;

/**
 * Groovy Util.
 *
 * @author Sven Augustus
 */
public class GroovyUtil {

  private GroovyUtil() {
  }

  /**
   * Groovy script root.
   */
  public static final String GROOVY_ROOT = "/groovy";

  public static String getRoot() {
    return getRoots()[0];
  }

  public static String[] getRoots() {
    String groovyPath = GroovyUtil.class.getResource(GROOVY_ROOT).getPath();
    return new String[]{groovyPath};
  }

  public static void setProperties(GroovyObject groovyObject, Bindings binding) {
    if (binding != null) {
      binding.forEach((k, v) -> {
        if (k != null) {
          groovyObject.setProperty(k, v);
        }
      });
    }
  }

  public static Object eval(GroovyObject groovyObject, Bindings binding)
      throws IllegalAccessException, InstantiationException {
    setProperties(groovyObject, binding);

    if (groovyObject instanceof Script) {
      Script script = (Script) groovyObject;
      return script.run();
    }
    // FIXME ?

    return null;
  }

  public static Object invoke(GroovyObject groovyObject, Bindings binding, String name,
      Object... args)
      throws IllegalAccessException, InstantiationException {
    setProperties(groovyObject, binding);

    if (groovyObject instanceof Script) {
      Script script = (Script) groovyObject;
      return script.invokeMethod(name, args);
    }

    return groovyObject.invokeMethod(name, args);
  }

}
