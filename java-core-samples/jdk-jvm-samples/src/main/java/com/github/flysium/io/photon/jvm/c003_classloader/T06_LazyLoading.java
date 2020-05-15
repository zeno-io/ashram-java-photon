package com.github.flysium.io.photon.jvm.c003_classloader;

/**
 * 严格讲应该叫 lazy Initializing ，因为java虚拟机规范并没有严格规定什么时候必须 loading ,但严格规定了什么时候 Initializing
 */
public class T06_LazyLoading {

  public static void main(String[] args) throws Exception {
    P p;

    // X x = new X();
    // System.out.println(P.i);

    // System.out.println(P.j);

    // Class.forName("com.github.flysium.io.photon.jvm.c003_classloader.T06_LazyLoading$P");
  }

  public static class P {

    final static int i = 8;
    static int j = 9;

    static {
      System.out.println("P");
    }
  }

  public static class X extends P {

    static {
      System.out.println("X");
    }
  }
}
