package com.github.flysium.io.sample.java2python.api;

/**
 * 欢迎
 *
 * @author Sven Augustus
 */
public class SayHello {

  private String userName;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void say(int i) {
    System.out.println(i + ":Hello " + userName);
  }

  @Override
  public String toString() {
    return "SayHello{" + "userName='" + userName + '\'' + '}';
  }
}
