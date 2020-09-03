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

package xyz.flysium.photon.c001_oop.polymorphism;

/**
 * 编译时多态 和 运行时多态 混合测试
 *
 * @author Sven Augustus
 */
public class MixTest {

	static class Human {

		public String sayHello(MixTest.Human human) {
			return "你好";
		}

		public String sayHello(MixTest.Man human) {
			return "您好,先生";
		}

		public String sayHello(MixTest.Woman human) {
			return "您好,美女";
		}

		/*public String sayHello(MixTest.Girl human) {
			return "您好,美少女";
		}*/
	}

	static class Man extends MixTest.Human {

		public String sayHello(MixTest.Human human) {
			return "你好,我是Y先生";
		}

		public String sayHello(MixTest.Woman human) {
			return "您好,美女,我是Y先生";
		}

		public String sayHello(MixTest.Girl human) {
			return "您好,美少女,我是Y先生";
		}

		// 先生对先生比较谨慎,没那么快介绍自己 =。=
	}

	static class Woman extends MixTest.Human {

		public String sayHello(MixTest.Human human) {
			return "你好,我是X美女";
		}

		public String sayHello(MixTest.Woman human) {
			return "您好,美女,我是X美女";
		}

		public String sayHello(MixTest.Girl human) {
			return "您好,美少女,我是X美女";
		}

		// 美女对先生比较含蓄,没那么快介绍自己 =。=
	}

	static class Girl extends MixTest.Woman {

		public String sayHello(MixTest.Human human) {
			return "你好,我是O美少女";
		}

	}

	public static void main(String[] args) {
		MixTest test = new MixTest();
		MixTest.Human guy = new MixTest.Human();
		MixTest.Human manAsGuy = new MixTest.Man();
		MixTest.Man man = new MixTest.Man();
		MixTest.Human womanAsGuy = new MixTest.Woman();
		MixTest.Woman woman = new MixTest.Woman();
		MixTest.Girl girl = new MixTest.Girl();

		System.out.print("假设大家在QQ等聊天软件上认识,这时候一般来招呼如下");
		System.out.println("当然先生对先生比较谨慎,没那么快介绍自己：");
		printMessage("一个人 欢迎 一个人", guy.sayHello(guy), "[我不想你知道我的性别,我也不知道你的性别,囧]");
		printMessage("一个人 欢迎 一名先生", guy.sayHello(man), "[我不想你知道我的性别,我知道你是一名先生,嘿嘿]");
		printMessage("一个人 欢迎 一名美女", guy.sayHello(woman), "[我不想你知道我的性别,我知道你是一名美女,哈哈]");
		printMessage("一个人[其实是先生] 欢迎 一个人", manAsGuy.sayHello(guy),
				"[我不想你知道我的性别,但是你知道我是先生,可是我不知道你的性别,汗]");
		printMessage("一个人[其实是先生] 欢迎 一个人[其实是先生]", manAsGuy.sayHello(manAsGuy),
				"[我不想你知道我的性别,但是你知道我是先生,可我不知道你的性别（或许你是一名先生）,呵]");
		printMessage("一个人[其实是先生] 欢迎 一个人[其实是美女]", manAsGuy.sayHello(womanAsGuy),
				"[我不想你知道我的性别,但是你知道我是先生,可我不知道你的性别（或许你是一名美女）,嘿]");
		printMessage("一个人[其实是先生] 欢迎 一名先生", manAsGuy.sayHello(man),
				"[我不想你知道我的性别,但是你知道我是先生,我知道你也是一名先生,呵呵]");
		printMessage("一个人[其实是先生] 欢迎 一名美女", manAsGuy.sayHello(woman),
				"[我不想你知道我的性别,但是你知道我是先生,我知道你是一名美女,噢噢]");
		printMessage("一个人[其实是先生] 欢迎 一名美少女", manAsGuy.sayHello(girl),
				"[我不想你知道我的性别,但是你知道我是先生,我知道你是一名美少女,噢]");
		printMessage("一名先生 欢迎 一个人 ", man.sayHello(guy), "[我是一名光明磊落的先生,可我不知道你的性别,额]");
		printMessage("一名先生 欢迎 一个人[其实是先生]", man.sayHello(manAsGuy),
				"[我是一名光明磊落的先生,可我不知道你的性别（或许你是一名先生）,咦]");
		printMessage("一名先生 欢迎 一个人[其实是美女]", man.sayHello(womanAsGuy),
				"[我是一名光明磊落的先生,可我不知道你的性别（或许你是一名美女）,嗯]");
		printMessage("一名先生 欢迎 一名先生", man.sayHello(man), "[我是一名光明磊落的先生,我知道你也是一名先生,非常好,我先观察]");
		printMessage("一名先生 欢迎 一名美女", man.sayHello(woman), "[我是一名光明磊落的先生,我知道你是一名美女,我先介绍自己]");
		printMessage("一名先生 欢迎 一名美少女", man.sayHello(girl), "[我是一名光明磊落的先生,我知道你是一名美少女,我先礼貌介绍自己]");
	}

	private static volatile int index = 1;

	private static void printMessage(String title, String message, String narrator) {
		System.out.println((index++) + "、" + String
				.format("%-35s%-20s%s", new String[]{title, message, narrator}));
	}

}
