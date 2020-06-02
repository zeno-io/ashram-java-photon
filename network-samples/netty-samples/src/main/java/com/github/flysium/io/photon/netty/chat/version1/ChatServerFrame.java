/*
 * Copyright 2020 SvenAugustus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.netty.chat.version1;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Chat Server Frame
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class ChatServerFrame extends JFrame {

  public static void main(String[] args) throws InterruptedException {
    ChatServerFrame serverFrame = new ChatServerFrame(new ChatServer(9090));
    serverFrame.start();
  }

  private final ChatServer server;
  private final Thread readFromConsole;

  private final JTextArea console = new JTextArea();

  public ChatServerFrame(final ChatServer server) throws HeadlessException {
    this.server = server;
    this.readFromConsole = new Thread(new UpdateConsoleRunnable(), "readFromConsole");

    this.console.setEditable(false);
    this.console.setLineWrap(true);
    this.console.setWrapStyleWord(true);
    JScrollPane scroll = new JScrollPane(this.console);
    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    this.add(scroll, BorderLayout.CENTER);

    this.setTitle("Chat Server");
    this.setSize(800, 600);
    this.setLocation(500, 150);
    this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }

  /**
   * Start
   */
  public void start() throws InterruptedException {
    this.setVisible(true);
    this.readFromConsole.start();
    this.server.start();
  }

  /**
   * update Console.
   *
   * @param text append String
   */
  private void updateConsole(String text) {
    if (text == null) {
      return;
    }
    this.console.append(System.lineSeparator());
    this.console.append(text);
  }

  class UpdateConsoleRunnable implements Runnable {

    @Override
    public void run() {
      while (true) {
        try {
          TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException exc) {
          exc.printStackTrace();
        }
        try {
          String readiedString = ChatServerFrame.this.server.readConsole();
          if (readiedString != null) {
            // update Console.
            updateConsole(readiedString);
          }
        } catch (InterruptedException exec) {
          exec.printStackTrace();
        }
      }
    }
  }

}
