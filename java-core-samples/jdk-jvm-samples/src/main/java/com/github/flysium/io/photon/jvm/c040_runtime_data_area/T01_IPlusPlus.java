package com.github.flysium.io.photon.jvm.c040_runtime_data_area;

/**
 * 输出是什么？ 8 还是 9 ？
 *
 * @author Sven Augustus
 */
public class T01_IPlusPlus {

  public static void main(String[] args) {
    int i = 8;
    i = i++;
    //i = ++i;
    System.out.println(i);
  }

}
