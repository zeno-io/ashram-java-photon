package io.flysium.sample.aspectj.advice;

public class AdviceDemo {

  public static void main(String[] args) {
    int i = main(5);
    System.out.println("result=" + i);
  }

  public static int main(int i) {
    return i;
  }

}
