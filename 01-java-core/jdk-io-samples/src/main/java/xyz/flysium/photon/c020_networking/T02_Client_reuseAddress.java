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

package xyz.flysium.photon.c020_networking;

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
