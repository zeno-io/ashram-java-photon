/*
 * MIT License
 *
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package xyz.flysium.photon.c001_serializable;

/**
 * @author Sven Augustus
 * @version 2016年11月20日
 */
public class Person
    implements
    java.io.Serializable/* , java.io.ObjectInputValidation */ {

  public Person(String fn, String ln, int a) {
    this.firstName = fn;
    this.lastName = ln;
    this.age = a;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public int getAge() {
    return age;
  }

  public Person getSpouse() {
    return spouse;
  }

  public void setFirstName(String value) {
    firstName = value;
  }

  public void setLastName(String value) {
    lastName = value;
  }

  public void setAge(int value) {
    age = value;
  }

  public void setSpouse(Person value) {
    spouse = value;
  }

  public String toString() {
    return "[Person: firstName=" + firstName + " lastName=" + lastName + " age=" + age
        + " spouse="
        + spouse.getFirstName() + "]";
  }

  private String firstName;
  private String lastName;
  private int age;
  private Person spouse;

  /** 1. 序列化允许重构 */
  /*
   * private String sss;
   *
   * public String getSss() { return sss; }
   *
   * public void setSss(String sss) { this.sss = sss; }
   */

  /** 2. 序列化并不安全 */
  /*
   * private void writeObject(java.io.ObjectOutputStream stream) throws
   * java.io.IOException { // "Encrypt"/obscure the sensitive data age = age
   * << 2; stream.defaultWriteObject(); }
   *
   * private void readObject(java.io.ObjectInputStream stream) throws
   * java.io.IOException, ClassNotFoundException {
   * stream.registerValidation(this, 1);//// 注册验证插件
   * stream.registerValidation(new ObjectInputValidation() {
   *
   * @Override public void validateObject() throws InvalidObjectException { if
   * (age < 40) { // 我们制作校验 throw new
   * InvalidObjectException("age is not right."); } } }, 1);//// 注册验证插件
   * stream.defaultReadObject(); // "Decrypt"/de-obscure the sensitive data
   * age = age >> 2; }
   */

  /**
   * 3.序列化允许将代理放在流中
   */
  private Object writeReplace() throws java.io.ObjectStreamException {
    return new PersonProxy(this);
  }

  /** 4.信任，但要验证 */
  /*
   * (non-Javadoc)
   *
   * @see java.io.ObjectInputValidation#validateObject()
   */
  /*
   * @Override public void validateObject() throws InvalidObjectException { if
   * (age < 0) { //if (age < 40) { // 我们制作校验 throw new
   * InvalidObjectException("age is not right."); } }
   */

}