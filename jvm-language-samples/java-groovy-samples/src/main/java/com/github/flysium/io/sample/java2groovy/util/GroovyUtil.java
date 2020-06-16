package com.github.flysium.io.sample.java2groovy.util;

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
