package io.flysium.sample.aspectj.weaver;

public class WeaverDemo {

  public static void main(String[] args) {
    main(1);
  }

  public static void main(int x) {
    System.out.println("args=" + x);
  }
}
