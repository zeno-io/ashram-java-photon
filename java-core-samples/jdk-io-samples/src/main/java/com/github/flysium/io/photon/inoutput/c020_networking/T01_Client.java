package com.github.flysium.io.photon.inoutput.c020_networking;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * simple client
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class T01_Client {

  public static void main(String[] args) throws Exception {
    Socket socket = new Socket("127.0.0.1", 9090);
    socket.setSoTimeout(3000);
    OutputStream out = socket.getOutputStream();

    System.out.println("-----------> send to server when you enter in console....");
    InputStream in = System.in;
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

    while (true) {
      String line = reader.readLine();
      if (line != null) {
        byte[] bb = line.getBytes();
        for (byte b : bb) {
          out.write(b);
        }
      }
    }
  }

}
