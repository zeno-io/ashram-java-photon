package com.github.flysium.io.photon.lambda;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Functional Interface
 *
 * @author Sven Augustus
 */
public class Test1 {

  public static void main(String[] args) {
    Function<String, Integer> f1 = String::length;
    System.out.println(f1.apply("abc"));

    Consumer<String> c = System.out::println;
    c.accept("str");
  }

}
