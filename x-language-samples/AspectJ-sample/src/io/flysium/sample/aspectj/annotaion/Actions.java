package io.flysium.sample.aspectj.annotaion;

@Bar
public class Actions {

  @Foo
  public void action() {
    System.out.println("Action!");
  }

  public void action2() {
    System.out.println("Go!");
  }
}
