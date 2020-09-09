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

package xyz.flysium.photon.samples.chat.version2;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import xyz.flysium.photon.Constant;
import xyz.flysium.photon.samples.chat.version2.model.InstantMessage;
import xyz.flysium.photon.samples.chat.version2.net.MessageChatClient;

/**
 * Chat Client Frame
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class MessageChatClientFrame extends JFrame {

  private static CountDownLatch countDownLatch;

  public static void main(String[] args) throws InterruptedException {
    Thread t1 = newThread(new MessageChatClientFrame(new Point(100, 100),
        Constant.HOST, Constant.PORT, 10, "C1"));
    Thread t2 = newThread(new MessageChatClientFrame(new Point(950, 100),
        Constant.HOST, Constant.PORT, 10, "C2"));
    t1.start();
    t2.start();

    countDownLatch = new CountDownLatch(2);
    countDownLatch.await();
    System.exit(0);
  }

  private static Thread newThread(MessageChatClientFrame c) {
    return new Thread(() -> {
      try {
        c.start();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
  }

  private final MessageChatClient client;
  private final Thread readFromServer;
  private final ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1,
      60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1024),
      Executors.defaultThreadFactory(), new CallerRunsPolicy());

  private final JTextArea chatBox = new JTextArea();
  private final JTextField input = new JTextField();

  public MessageChatClientFrame(final Point point, String host, int port, int pollTimeout,
      String userId) throws HeadlessException {
    this.client = new MessageChatClient(host, port, pollTimeout, userId);
    this.readFromServer = new Thread(new UpdateChatBoxRunnable(), "readFromServer");

    this.chatBox.setEditable(false);
    this.chatBox.setLineWrap(true);
    this.chatBox.setWrapStyleWord(true);
    this.input.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        String text = input.getText();
        // write to server
        executor.submit(() -> {
          MessageChatClientFrame.this.client.sendMessage(text);
        });
        // update Message box.
        updateChatBox(LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " Me> " + text);
        input.setText("");
      }
    });
    JScrollPane scroll = new JScrollPane(this.chatBox);
    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    this.add(scroll, BorderLayout.CENTER);
    this.add(input, BorderLayout.SOUTH);

    this.setTitle("Message Chat Client (" + this.client.getUserId() + ")");
    this.setSize(800, 600);
    this.setLocation(point);
    this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
//        System.exit(0);
        MessageChatClientFrame.this.client.stop();
        countDownLatch.countDown();
      }
    });
  }

  /**
   * Start
   */
  public void start() throws InterruptedException {
    this.setVisible(true);
    this.readFromServer.start();
    this.client.start();
  }

  /**
   * update Message box.
   *
   * @param text append String
   */
  private void updateChatBox(String text) {
    if (text == null) {
      return;
    }
    synchronized (this.chatBox) {
      this.chatBox.append(System.lineSeparator());
      this.chatBox.append(text);
    }
  }

  class UpdateChatBoxRunnable implements Runnable {

    @Override
    public void run() {
      while (true) {
        try {
          TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException exc) {
          exc.printStackTrace();
        }
        try {
          InstantMessage instantMessage = MessageChatClientFrame.this.client.readMessage();
          if (instantMessage != null) {
            // update Message box.
            updateChatBox(instantMessage.toString());
          }
        } catch (InterruptedException exec) {
          exec.printStackTrace();
        }
      }
    }
  }

}
