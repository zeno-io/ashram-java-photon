package com.github.flysium.io.photon.inoutput.c020_networking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

/**
 * simple server
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class T02_Server {

  public static void main(String[] args) throws IOException, InterruptedException {
    ServerSocket serverSocket = new ServerSocket();
    serverSocket.bind(new InetSocketAddress(9090), 2);
    // 测试用
    System.out.println("-----------> start to accept clients when you enter in console....");
    System.in.read();
  }

}
