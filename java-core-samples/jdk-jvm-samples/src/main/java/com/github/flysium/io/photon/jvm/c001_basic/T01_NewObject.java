package com.github.flysium.io.photon.jvm.c001_basic;

/**
 * T01_NewObject t = new T01_NewObject() 的字节码
 */
public class T01_NewObject {

  int m = 8;

  public static void main(String[] args) {
    T01_NewObject t = new T01_NewObject();
//        new :  申请内存 - 默认值 - 初始值
//        invokerspecial  T01_NewObject.<init> : 调用默认构造函数
//        astrore :  赋值给 T01_NewObject t
  }
}
