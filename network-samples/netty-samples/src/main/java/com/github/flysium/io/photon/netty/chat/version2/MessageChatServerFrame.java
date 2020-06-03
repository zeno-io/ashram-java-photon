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

package com.github.flysium.io.photon.netty.chat.version2;

import com.github.flysium.io.photon.netty.chat.version2.model.InstantMessage;
import com.github.flysium.io.photon.netty.chat.version2.net.MessageChatServer;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
public class MessageChatServerFrame extends JFrame {

  public static void main(String[] args) throws InterruptedException {
    MessageChatServerFrame serverFrame = new MessageChatServerFrame(9099);
    serverFrame.start();
  }

  private final MessageChatServer server;
  private final Thread readFromConsole;

  private final Map<String, String> channel2UserId = new ConcurrentHashMap<>();

  private final JTextArea console = new JTextArea();

  public MessageChatServerFrame(final int port) throws HeadlessException {
    this.server = new MessageChatServer(port, (channelId, userId) -> {
      if (channel2UserId.get(channelId) == null) {
        channel2UserId.putIfAbsent(channelId, userId);
        MessageChatServerFrame.this.server.writeToOthers(channelId,
            InstantMessage.serverMessage(userId + " join."));
      }
    }, (channelId) -> {
      String userId = channel2UserId.get(channelId);
      if (userId != null) {
        MessageChatServerFrame.this.server.writeToOthers(channelId,
            InstantMessage.serverMessage(userId + " leave."));
        channel2UserId.remove(channelId);
      }
    });
    this.readFromConsole = new Thread(new UpdateConsoleRunnable(), "readFromConsole");

    this.console.setEditable(false);
    this.console.setLineWrap(true);
    this.console.setWrapStyleWord(true);
    JScrollPane scroll = new JScrollPane(this.console);
    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    this.add(scroll, BorderLayout.CENTER);

    this.setTitle("Message Chat Server (" + port + ")");
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
          String readiedString = MessageChatServerFrame.this.server.readConsole();
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
