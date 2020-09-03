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
package com.github.flysium.io.photon.jvm.agent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.security.ProtectionDomain;
import java.util.Map;
import java.util.Objects;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 热部署
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class HotAgent {

  private static final ScheduledThreadPoolExecutor SCHEDULED_THREAD_POOL_EXECUTOR = new ScheduledThreadPoolExecutor(
      1);

  public static void premain(String agentArgs, Instrumentation inst) throws Exception {
    // ClassFileTransformer transformer = new ClassTransform(inst);
    // inst.addTransformer(transformer);
    System.out.println("是否支持类的重定义：" + inst.isRedefineClassesSupported());
    SCHEDULED_THREAD_POOL_EXECUTOR.scheduleAtFixedRate(new ReloadTask(inst),
        2000, 2000, TimeUnit.MILLISECONDS);
  }

}

class ClassTransform implements ClassFileTransformer {

  private final Instrumentation inst;

  protected ClassTransform(Instrumentation inst) {
    this.inst = inst;
  }

  /**
   * 此方法在 redefineClasses 时或者初次加载时会调用，也就是说在 class 被再次加载时会被调用，并且我们通过此方法可以动态修改class字节码，实现类似代理之类的功能。
   * <p>
   * 具体方法可使用 ASM 或者 javassist， 如果对字节码很熟悉的话可以直接修改字节码。
   */
  @Override
  public byte[] transform(ClassLoader loader, String className,
      Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
      byte[] classFileBuffer) throws IllegalClassFormatException {
    byte[] transformed = null;
    return null;
  }

}

class ReloadTask extends TimerTask {

  private final Instrumentation inst;

  protected ReloadTask(Instrumentation inst) {
    this.inst = inst;
  }

  protected static Map<String, Long> classLastModified = new ConcurrentHashMap<>();

  @Override
  public void run() {
    try {
      ClassDefinition[] cd = new ClassDefinition[1];
      Class<?>[] classes = inst.getAllLoadedClasses();
      for (Class<?> cls : classes) {
        ClassLoader loader = cls.getClassLoader();
        String className = cls.getName();
        if (loader == null) {
          continue;
        }
        // exclude
        if (HotAgent.class.getName().equals(className)
            || ClassTransform.class.getName().equals(className)
            || ReloadTask.class.getName().equals(className)) {
          continue;
        }
        String name = className.replaceAll("\\.", "/");
        URL url = ClassLoader.getSystemClassLoader().getResource(name + ".class");

        Long lastModified = classLastModified.get(className);
        try {
          long currentLastModified = lastModified(url);
          if (lastModified == null || lastModified < currentLastModified) {
            System.out.println("redefineClasses~ " + className);
            byte[] classBytes = loadClassBytes(cls, name + ".class");
            cd[0] = new ClassDefinition(cls, Objects.requireNonNull(classBytes));
            inst.redefineClasses(cd);
            lastModified = currentLastModified;
            classLastModified.put(className, lastModified);
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * @see org.springframework.core.io.DefaultResourceLoader#getResource()#lastModified()
   */
  private long lastModified(URL url) throws IOException {
    File file = new File(url.getFile());
    return file.lastModified();
  }

  private byte[] loadClassBytes(Class<?> cls, String className) throws Exception {
    try (InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(className)) {
      if (is == null) {
        return new byte[0];
      }
      byte[] bt = new byte[is.available()];
      is.read(bt);
      return bt;
    }
  }

}

