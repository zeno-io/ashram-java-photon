package com.github.flysium.io.photon.jvm.agent;

import java.lang.instrument.Instrumentation;

/**
 * 计算对象大小 Agent
 *
 * @author Sven Augustus
 */
public class ObjectSizeAgent {

  private static Instrumentation instrumentation;

  public static void premain(String agentArgs, Instrumentation inst) {
    instrumentation = inst;
  }

  /**
   * 返回对象的大小
   *
   * @param o 对象实例
   * @return 对象的大小
   */
  public static long sizeOf(Object o) {
    return instrumentation.getObjectSize(o);
  }

}
