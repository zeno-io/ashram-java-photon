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

package xyz.flysium.photon.c000_basic;

/**
 * try{}里有一个return语句，那么紧跟在这个try后的finally{}里的代码会不会被执行，什么时候被执行，在return前还是后?
 *
 * @author Sven Augustus
 * @version 2016年10月30日
 */
public class FinallyTest {

	public static void main(String[] args) {
		System.out.println(testFinally());
	}

	/**
	 * 注意：在finally中改变返回值的做法是不好的， 因为如果存在finally代码块，try中的return语句不会立马返回调用者，
	 * 而是记录下返回值待finally代码块执行完毕之后再向调用者返回其值， 然后如果在finally中修改了返回值，就会返回修改后的值。
	 * <p>
	 * 显然，在finally中返回或者修改返回值会对程序造成很大的困扰， C#中直接用编译错误的方式来阻止程序员干这种龌龊的事情， Java中也可以通过提升编译器的语法检查级别来产生警告或错误，
	 * Eclipse中可以在如图所示的地方进行设置，强烈建议将此项设置为编译错误。
	 */
	@SuppressWarnings("finally")
	static int testFinally() {
		int result = 0;
		try {
			// something to do
			result = 1;
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			result = 2;
			return result;
		}
	}

}
