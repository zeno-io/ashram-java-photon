package io.flysium.sample.aspectj.within;

public class WithinDemo {

  public static void main(String[] args) {
    main(1);
    }
  
  public static void main(int i) {
    System.out.println("within for WithinDemo,args=" +i );  
   }
  
}
