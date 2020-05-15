/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.inoutput.c002_nio.s03_piped_channel;

import com.github.flysium.io.photon.inoutput.c002_nio.ByteBufferUtils;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

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
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
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
