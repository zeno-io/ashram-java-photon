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
 * 序列化允许将代理放在流中
 * <p>
 * 为原始 Person 提供一个 writeReplace 方法，可以序列化不同类型的对象来代替它。 类似地，如果反序列化期间发现一个 readResolve
 * 方法，那么将调用该方法，将替代对象提供给调用者。
 *
 * @author Sven Augustus
 * @version 2016年11月20日
 */
public class PersonProxy implements java.io.Serializable {

  private static final long serialVersionUID = -671942554578006397L;

  public PersonProxy(Person orig) {
    data = orig.getFirstName() + "," + orig.getLastName() + "," + orig.getAge();
    if (orig.getSpouse() != null) {
      Person spouse = orig.getSpouse();
      data = data + "," + spouse.getFirstName() + "," + spouse.getLastName() + "," + spouse
          .getAge();
    }
  }

  public String data;

  private Object readResolve() throws java.io.ObjectStreamException {
    String[] pieces = data.split(",");
    Person result = new Person(pieces[0], pieces[1], Integer.parseInt(pieces[2]));
    if (pieces.length > 3) {
      result.setSpouse(new Person(pieces[3], pieces[4], Integer.parseInt(pieces[5])));
      result.getSpouse().setSpouse(result);
    }
    return result;
  }

}