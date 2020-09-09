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

package xyz.flysium.photon.c000_bio.s07_piped;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * 管道测试1
 *
 * @author Sven Augustus
 * @version 2017年1月26日
 */
public class PipedTest1 {

	static class Input implements Runnable {

		private final PipedInputStream inputStream = new PipedInputStream();

		public Input() {
		}

		public PipedInputStream getInputStream() {
			return inputStream;
		}

		@Override
		public void run() {
			try {
				byte[] buf = new byte[1024];
				int len = -1;
				System.out.println("管道读取准备。");
				StringBuffer result = new StringBuffer();

				while ((len = inputStream.read(buf)) > 0) {
					// System.out.println(new String(buf, 0, len));
					result.append(new String(buf, 0, len));
				}

				System.out.println("管道读取结果：" + result.toString());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	static class Output implements Runnable {

		private final PipedOutputStream outputStream = new PipedOutputStream();

		public Output() {
		}

		public PipedOutputStream getOutputStream() {
			return outputStream;
		}

		@Override
		public void run() {
			try {
				System.out.println("管道写出准备。");
				StringBuilder sb = new StringBuilder();
				// 模拟 通过for循环写入2050个字节
				for (int i = 0; i < 201; i++) {
					sb.append("0123456789");
					if (i > 0 && (i % 10 == 0)) {
						sb.append("\r\n");
					}
				}
				String str = sb.toString();
				outputStream.write(str.getBytes());
				System.out.println("管道写出完成。");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (outputStream != null) {
						outputStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void main(String[] args) throws IOException {
		Input input = new Input();
		Output output = new Output();
		/**
		 * 将“管道输入流”和“管道输出流”关联起来。
		 */
		// input.getInputStream().connect(output.getOutputStream());// 与下面一行等价
		output.getOutputStream().connect(input.getInputStream());

		new Thread(input).start();
		new Thread(output).start();
	}

}
