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

import groovy.util.ResourceException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import javax.script.Bindings;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import xyz.flysium.photon.util.GroovyClassLoaderUtil;
import xyz.flysium.photon.util.GroovyScriptEngineJsr223Util;
import xyz.flysium.photon.util.GroovyScriptEngineUtil;
import xyz.flysium.photon.util.GroovyShellUtil;

/**
 * Test Java To Groovy.
 *
 * @author Sven Augustus
 */
public class Java2GroovyTest {

  public static void main(String[] args)
      throws ScriptException, NoSuchMethodException, InstantiationException,
      IllegalAccessException, IOException, ResourceException, groovy.util.ScriptException {
    testGroovyEval();
    testGroovyEval2();
    testGroovyClass();
    testGroovyFile();
  }

  public static void testGroovyEval()
      throws IllegalAccessException, InstantiationException, IOException, ScriptException {
    String scriptText = "3*5";

    // GroovyShell
    Object result = GroovyShellUtil.eval(scriptText, null);
    System.out.println(scriptText + "=" + result);

    // GroovyClassLoader
    result = GroovyClassLoaderUtil.eval(scriptText, null);
    System.out.println(scriptText + "=" + result);

    // ScriptEngine JSR 223 javax.script API.
    result = GroovyScriptEngineJsr223Util.eval(scriptText, null);
    System.out.println(scriptText + "=" + result);
  }

  public static void testGroovyEval2()
      throws IllegalAccessException, InstantiationException, IOException, ScriptException, NoSuchMethodException {
    String scriptText = "def getTime(){return date.getTime();}\ndef sayHello(name,age){return 'Hello, I am ' + name + ' , age is ' + age;}";

    Bindings binding = new SimpleBindings();
    binding.put("date", new Date());

    // GroovyShell
    Object result = GroovyShellUtil.invoke(scriptText, binding, "getTime");
    System.out.println("getTime=" + result);
    result = GroovyShellUtil.invoke(scriptText, binding, "sayHello", "SvenAugustus", 29);
    System.out.println("sayHello=" + result);

    // GroovyClassLoader
    result = GroovyClassLoaderUtil.invoke(scriptText, binding, "getTime");
    System.out.println("getTime=" + result);
    result = GroovyClassLoaderUtil.invoke(scriptText, binding, "sayHello", "SvenAugustus", 29);
    System.out.println("sayHello=" + result);

    // ScriptEngine JSR 223 javax.script API.
    result = GroovyScriptEngineJsr223Util.invoke(scriptText, binding, "getTime");
    System.out.println("getTime=" + result);
    result = GroovyScriptEngineJsr223Util
        .invoke(scriptText, binding, "sayHello", "SvenAugustus", 29);
    System.out.println("sayHello=" + result);
  }

  public static void testGroovyClass()
      throws IllegalAccessException, InstantiationException, IOException, ScriptException, NoSuchMethodException {
    String scriptText = "class A {\n" + "\n" + "    def sayHello(name, age) {\n"
        + "        return \"Hello, I'm \" + name + \" , age is \" + age;\n" + "    }\n" + "}";

    // GroovyClassLoader
    Object result = GroovyClassLoaderUtil.invoke(scriptText, null, "sayHello", "SvenAugustus", 29);
    System.out.println("A.sayHello=" + result);

    // ScriptEngine JSR 223 javax.script API.
    result = GroovyScriptEngineJsr223Util.invoke(scriptText, null, "sayHello", "SvenAugustus", 29);
    System.out.println("A.sayHello=" + result);
  }

  public static void testGroovyFile()
      throws IllegalAccessException, InstantiationException, IOException,
      ScriptException, NoSuchMethodException, ResourceException, groovy.util.ScriptException {
    String groovyPath = Java2GroovyTest.class.getResource("/groovy")
        .getPath();
    String scriptFile = "cls.groovy";
    String scriptFile2 = "cls2.groovy";

    // GroovyClassLoader
    Object result = GroovyClassLoaderUtil
        .invoke(new FileReader(new File(groovyPath, scriptFile)), null, "sayHello",
            "SvenAugustus", 29);
    System.out.println("A.sayHello=" + result);

    // GroovyScriptEngine
    result = GroovyScriptEngineUtil.invokeFile(scriptFile, null, "sayHello", "SvenAugustus", 29);
    System.out.println("A.sayHello=" + result);

    // ScriptEngine JSR 223 javax.script API.
    result = GroovyScriptEngineJsr223Util
        .invoke(new FileReader(new File(groovyPath, scriptFile)), null, "sayHello",
            "SvenAugustus", 29);
    System.out.println("A.sayHello=" + result);

    // Person
    Person person = new Person("SvenAugustus", 29);
    result = GroovyClassLoaderUtil
        .invoke(new FileReader(new File(groovyPath, scriptFile2)), null, "sayHello", person);
    System.out.println("E.sayHello=" + result);

  }

}
