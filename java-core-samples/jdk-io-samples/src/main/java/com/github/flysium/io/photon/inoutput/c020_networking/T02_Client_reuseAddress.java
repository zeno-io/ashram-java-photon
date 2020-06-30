package com.github.flysium.io.photon.inoutput.c020_networking;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * SO_RESUSEADDR 表示是否允许重用Socket所绑定的本地地址
 *
 * <li>
 * netstat -tnap # 查看本地客户端连接服务端，采用绑定的端口就是指定的端口，而不再是匿名的随机端口
 * </li>
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class T02_Client_reuseAddress {

  public static void main(String[] args) throws Exception {
    Socket socket = new Socket(); //此时Socket对象未绑定到本地端口，并且未连接远程服务器
    socket.setReuseAddress(true);
    SocketAddress localAddr = new InetSocketAddress("localhost", 19999);
    SocketAddress remoteAddr = new InetSocketAddress("127.0.0.1", 9090);
    socket.bind(localAddr); //与本地端口绑定
    socket.connect(remoteAddr); //连接远程服务器，并且绑定匿名的本地端口
  }

}
