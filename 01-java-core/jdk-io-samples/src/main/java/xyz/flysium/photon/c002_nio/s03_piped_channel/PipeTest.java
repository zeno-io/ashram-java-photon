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

package xyz.flysium.photon.c002_nio.s03_piped_channel;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import xyz.flysium.photon.c002_nio.ByteBufferUtils;

/**
 * 管道测试
 *
 * @author Sven Augustus
 */
public class PipeTest {

	static class Input implements Runnable {

		private final Pipe pipe;

		public Input(Pipe pipe) {
			this.pipe = pipe;
		}

		@Override
		public void run() {
			try {
				Pipe.SourceChannel sourceChannel = pipe.source();

				System.out.println("管道读取准备。");
				ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
				int bytesRead = sourceChannel.read(byteBuffer);
				Serializable serializable = ByteBufferUtils.readObject(byteBuffer);
				System.out.println("管道读取结果：" + serializable);
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	static class Output implements Runnable {

		private final Pipe pipe;

		public Output(Pipe pipe) {
			this.pipe = pipe;
		}

		@Override
		public void run() {
			try {
				Pipe.SinkChannel sinkChannel = pipe.sink();

				System.out.println("管道写出准备。");
				Serializable object = "您好啊，Pipe。";
				ByteBuffer byteBuffer = ByteBufferUtils.writeObject(object);
				while (byteBuffer.hasRemaining()) {
					sinkChannel.write(byteBuffer);
				}
				System.out.println("管道写出完成：" + object);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Pipe pipe = Pipe.open();
		new Thread(new Input(pipe)).start();
		new Thread(new Output(pipe)).start();
	}

}
