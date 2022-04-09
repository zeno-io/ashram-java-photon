package io.flysium.sample.aspectj.cflow;

public class CflowDemo {

  public static void main(String[] args) {
    new CflowDemo().display();
  }
  
  public void display() {
    sound();
    color();
  }
  
  public void sound() {
     System.out.println("CflowDemo.sound");
  }
  
 public void color() {
   sound();
   System.out.println("CflowDemo.color");

  }

}
