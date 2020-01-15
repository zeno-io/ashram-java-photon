package com.github.flysium.io.photon.inoutput.c002_nio.s02_file_channel.lock;

/**
 * 非阻塞写
 *
 * @author Sven Augustus
 */
public class FileLockMain_wTry extends FileChannelTest2 {

	public static void main(String[] args) throws InterruptedException {
		final String path = "file.txt";
		// 非阻塞写
		writeTry(path, "线程B");
	}

}
