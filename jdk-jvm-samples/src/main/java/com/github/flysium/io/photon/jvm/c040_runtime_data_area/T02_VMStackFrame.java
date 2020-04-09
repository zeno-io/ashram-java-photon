package com.github.flysium.io.photon.jvm.c040_runtime_data_area;

public class T02_VMStackFrame {

  public static void main(String[] args) {
    int i = 100;
  }

  public void m1() {
    int i = 200;
  }

  public void m2(int k) {
    int i = 300;
  }

  public void add(int a, int b) {
    int c = a + b;
  }

  public void m3() {
    Object o = null;
  }

  public void m4() {
    Object o = new Object();
  }

}
