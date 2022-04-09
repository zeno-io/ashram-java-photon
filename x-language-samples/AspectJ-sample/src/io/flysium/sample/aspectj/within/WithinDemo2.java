package io.flysium.sample.aspectj.within;

public class WithinDemo2 {

  public static void main(String[] args) {
    main(2);
    }
  
  public static void main(int i) {
    System.out.println("within for WithinDemo2,args=" +i );  
   }
  
}
