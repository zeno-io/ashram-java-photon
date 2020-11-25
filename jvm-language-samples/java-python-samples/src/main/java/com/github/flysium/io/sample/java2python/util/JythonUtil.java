package com.github.flysium.io.sample.java2python.util;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.MapUtils;
import org.python.core.PyCode;
import org.python.core.PyObject;
import org.python.core.PySystemState;
import org.python.google.common.cache.Cache;
import org.python.google.common.cache.CacheBuilder;
import org.python.google.common.util.concurrent.UncheckedExecutionException;
import org.python.util.PythonInterpreter;

/**
 * Jython（Python for the Java Platform）工具类
 *
 * @author Sven Augustus
 * @see <a href="https://github.com/jythontools/jython">A mirror of hg.python.org</a>
 * @see <a href="https://wiki.python.org/jython/">Jython Wiki</a>
 * @see <a href="https://wiki.python.org/jython/DocumentationAndEducation/">Jython Documentation and
 * Learning</a>
 */
public final class JythonUtil {

  private JythonUtil() {
  }

  /**
   * 编译结果缓存
   */
  private static final Cache<String, PyCode> CODE_CACHE = CacheBuilder.newBuilder()
    .maximumSize(1000).build();

  /**
   * Python 环境配置
   */
  private static final PySystemState PY_SYSTEM_STATE = new PySystemState();

  static {
    // Python 系统状态,可根据需要指定 ClassLoader /sys. stdin/sys. stdout 等
    PySystemState.initialize();
    PY_SYSTEM_STATE.setClassLoader(Thread.currentThread().getContextClassLoader());
    PY_SYSTEM_STATE.setdefaultencoding(StandardCharsets.UTF_8.name());
  }

  /**
   * 获取 Python 解释器
   *
   * @return Python 解释器
   */
  private static PythonInterpreter getPythonInterpreter() {
    //
    // Notice: PythonInterpreter is thread-safe.
    // But the exec and execfile methods of PythonInterpreter are not.
    //
    return new PythonInterpreter(null, PY_SYSTEM_STATE);
  }

  /**
   * 获取缓存 key
   *
   * @param scriptText 脚本内容
   * @return 缓存 key
   */
  private static String cacheKey(String scriptText) {
    return DigestUtils.sha256Hex(scriptText);
  }

  /**
   * 对 Python 脚本预编译
   *
   * @param interpreter Python 解释器实例
   * @param scriptText  Python 脚本内容
   * @return Python 编译结果
   */
  @SuppressWarnings("PMD.PreserveStackTrace")
  private static PyCode precompile(PythonInterpreter interpreter, String scriptText) {
    if (interpreter == null || "".equals(scriptText)) {
      return null;
    }
    String key = cacheKey(scriptText);

    try {
      return CODE_CACHE.get(key, () -> interpreter.compile(scriptText));
    } catch (ExecutionException | UncheckedExecutionException e) {
      throw new IllegalStateException("Failed to compile Python script", e.getCause());
    } catch (RuntimeException e) {
      throw new IllegalStateException("Failed to compile Python script", e);
    }
  }

  /**
   * 对 Python 脚本预编译
   *
   * @param scriptText Python 脚本内容
   */
  public static void precompile(String scriptText) {
    PythonInterpreter interpreter = getPythonInterpreter();

    precompile(interpreter, scriptText);
  }

  /**
   * 执行 Python 脚本
   *
   * @param interpreter Python 解释器实例
   * @param scriptText  Python 脚本内容
   * @param arguments   绑定变量
   */
  private static void exec(PythonInterpreter interpreter, String scriptText,
    Map<String, Object> arguments) {
    if (interpreter == null) {
      return;
    }
    PyCode pycode = precompile(interpreter, scriptText);
    if (pycode == null) {
      return;
    }
    // 设置 Python 属性, Python 脚本中可以使用
    if (MapUtils.isNotEmpty(arguments)) {
      for (Entry<String, Object> entry : arguments.entrySet()) {
        interpreter.set(entry.getKey(), entry.getValue());
      }
    }

    // 执行 Python 脚本
    interpreter.exec(pycode);
  }

  /**
   * 执行 Python 脚本
   *
   * @param scriptText Python 脚本内容
   */
  public static void exec(String scriptText) {
    exec(scriptText, null);
  }

  /**
   * 执行 Python 脚本
   *
   * @param scriptText Python 脚本内容
   * @param arguments  绑定变量
   */
  public static void exec(String scriptText, Map<String, Object> arguments) {
    PythonInterpreter interpreter = getPythonInterpreter();

    exec(interpreter, scriptText, arguments);
  }

  /**
   * 执行 Python 脚本并返回 Java 结果
   *
   * @param scriptText Python 脚本内容
   * @param returnName 返回结果变量名
   * @param returnType 返回结果变量Java类型
   * @return 执行结果
   */
  public static <T> T computeToJavaResult(String scriptText, String returnName,
    Class<T> returnType) {
    return computeToJavaResult(scriptText, null, returnName, returnType);
  }

  /**
   * 执行 Python 脚本并返回 Java 结果
   *
   * @param scriptText         Python 脚本内容
   * @param arguments          绑定变量
   * @param returnVariableName 返回结果变量名
   * @param returnType         返回结果变量Java类型
   * @return 执行结果
   */
  @SuppressWarnings("unchecked")
  public static <T> T computeToJavaResult(String scriptText, Map<String, Object> arguments,
    String returnVariableName,
    Class<T> returnType) {

    PythonInterpreter interpreter = getPythonInterpreter();

    exec(interpreter, scriptText, arguments);

    PyObject result = interpreter.get(returnVariableName);

    return (T) result.__tojava__(returnType);
  }

  /**
   * 解析并执行 Python 脚本文件
   *
   * @param scriptFile Groovy 脚本文件路径
   * @param arguments  绑定变量
   */
  public static void execfile(File scriptFile, Map<String, Object> arguments) {
    PythonInterpreter interpreter = getPythonInterpreter();

    // 设置 Python 属性, Python 脚本中可以使用
    if (MapUtils.isNotEmpty(arguments)) {
      for (Entry<String, Object> entry : arguments.entrySet()) {
        interpreter.set(entry.getKey(), entry.getValue());
      }
    }

    // 执行 Python 脚本
    interpreter.execfile(scriptFile.getAbsolutePath());
  }

}
