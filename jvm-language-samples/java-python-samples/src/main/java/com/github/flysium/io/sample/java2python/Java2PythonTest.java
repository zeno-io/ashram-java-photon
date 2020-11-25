package com.github.flysium.io.sample.java2python;

import com.github.flysium.io.sample.java2python.api.SayHello;
import com.github.flysium.io.sample.java2python.util.JythonUtil;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

/**
 * 测试java和python的调用流程
 *
 * @author Sven Augustus
 */
public class Java2PythonTest {

  public static void main(String[] args) {
    testExec();

    testExec2();

    testSource();

    testSourceToJava();

    testFile();
  }

  private static void testExec() {
    PythonInterpreter interp = new PythonInterpreter();

    System.out.println("Hello, brave new world");
    interp.exec("import sys");
    interp.exec("print sys");

    interp.set("a", new PyInteger(42));
    interp.exec("print a");
    interp.exec("x = 2+2");
    PyObject x = interp.get("x");

    System.out.println("x: " + x);

    interp.exec("import sys");
    interp.exec("print sys");

    interp.set("a", new PyInteger(43));
    interp.exec("print a");
    interp.exec("x = 2+3");
    x = interp.get("x");

    System.out.println("Goodbye, cruel world");

    System.out.println("------------------------");
  }

  private static void testExec2() {
    PythonInterpreter interp = new PythonInterpreter();

    String scriptText =
      // @formatter:off
      "def mul(x, y):\n"
        + "  return x * y \n"
        + "result = mul(5, 7)\n"
        + "print result\n";
    // @formatter:on

    JythonUtil.exec(scriptText);

    System.out.println("------------------------");
  }

  private static void testSource() {
    Map<String, Object> arguments = new HashMap<String, Object>(8);

    String scriptText =
      // @formatter:off
      "class Dog:\n"
        + "    def __init__(self, bark_text):\n"
        + "        self.bark_text = bark_text\n"
        + "        return\n" + "\n" + "    def bark(self):\n"
        + "        print self.bark_text\n"
        + "        return\n" + "    def annoy_neighbors(self, degree):\n"
        + "        for i in range(degree):\n"
        + "            print self.bark_text\n"
        + "        return\n"
        + "\n"
        + "print \"Fido is born\"\n"
        + "fido = Dog(\"Bow wow\")\n"
        + "\n" + "print \"Let's hear from Fido\"\n" + "\n"
        + "fido.bark()\n"
        + "\n"
        + "print \"Time to annoy the neighbors\"\n"
        + "fido.annoy_neighbors(5)";
    // @formatter:on

    JythonUtil.exec(scriptText, arguments);
    System.out.println("------------------------");
  }

  private static void testSourceToJava() {
    Map<String, Object> arguments = new HashMap<String, Object>(8);
    arguments.put("userName", "SvenAugustus");

    String scriptText =
      // @formatter:off
      "from com.github.flysium.io.sample.java2python.api import SayHello\n"
        + "result = SayHello()\n"
        + "result.setUserName(userName)\n";
    // @formatter:on

    SayHello sayHello = JythonUtil
      .computeToJavaResult(scriptText, arguments, "result", SayHello.class);
    System.out.println(sayHello);
    System.out.println("------------------------");
  }

  private static void testFile() {
    String pythonPath = Java2PythonTest.class.getResource("/python").getPath();
    File scriptFile = new File(pythonPath, "test.py");

    Map<String, Object> arguments = new HashMap<String, Object>(8);
    arguments.put("randomlength", 6);

    JythonUtil.execfile(scriptFile, arguments);
    System.out.println("------------------------");
  }
}
