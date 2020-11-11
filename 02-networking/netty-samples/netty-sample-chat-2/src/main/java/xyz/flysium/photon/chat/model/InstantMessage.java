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

package xyz.flysium.photon.chat.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * message.
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class InstantMessage implements java.io.Serializable {

  private static final long serialVersionUID = -8097657999896501515L;

  private long createTime;

  private int type;

  private String userId;

  private String content;

  public InstantMessage() {
  }

  public InstantMessage(long createTime, int type, String userId, String content) {
    this.createTime = createTime;
    this.type = type;
    this.userId = userId;
    this.content = content;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public MessageType ofType() {
    return MessageType.values()[type];
  }

  public static InstantMessage serverMessage(String content) {
    return new InstantMessage(Instant.now().toEpochMilli(), MessageType.SERVER.ordinal(), null,
        content);
  }

  public static InstantMessage systemMessage(String content) {
    return new InstantMessage(Instant.now().toEpochMilli(), MessageType.SYSTEM.ordinal(), null,
        content);
  }

  public static InstantMessage echoMessage(String userId) {
    return new InstantMessage(Instant.now().toEpochMilli(), MessageType.ECHO.ordinal(), userId,
        null);
  }

  public static InstantMessage userMessage(String userId, String content) {
    return new InstantMessage(Instant.now().toEpochMilli(), MessageType.USER.ordinal(), userId,
        content);
  }

  @Override
  public String toString() {
    String nowString = LocalDateTime
        .ofInstant(Instant.ofEpochMilli(createTime), ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    if (type == MessageType.USER.ordinal()) {
      return nowString + " " + userId + "> " + content;
    }
    if (type == MessageType.ECHO.ordinal()) {
      return nowString + " ECHO> " + userId;
    }
    if (type == MessageType.SERVER.ordinal()) {
      return nowString + " SERVER> " + content;
    }
    return nowString + " SYSTEM> " + content;
  }

}
