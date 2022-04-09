package io.flysium.sample.aspectj.withcode;

public class WithcodeDemo {

  public static void main(String[] args) {
    method(5);
   test(6);
   }

  public static void test(int i) {
    method(i); 
   }
  
  public static void method(int i) {
    System.out.println("withincode,args=" +i );  
   }
  
}
