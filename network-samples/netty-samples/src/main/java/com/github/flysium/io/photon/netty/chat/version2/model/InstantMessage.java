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

package com.github.flysium.io.photon.netty.chat.version2.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

  private final long createTime;

  private final MessageType type;

  private final String userId;

  private final String content;

  public InstantMessage(long createTime, short ordinal, String userId, String content) {
    this(createTime, MessageType.values()[ordinal], userId, content);
  }

  public InstantMessage(long createTime, MessageType type, String userId, String content) {
    this.createTime = createTime;
    this.type = type;
    this.userId = userId;
    this.content = content;
  }

  public long getCreateTime() {
    return createTime;
  }

  public MessageType getType() {
    return type;
  }

  public String getUserId() {
    return userId;
  }

  public String getContent() {
    return content;
  }

  public static InstantMessage serverMessage(String content) {
    return new InstantMessage(Instant.now().toEpochMilli(), MessageType.SERVER, null, content);
  }

  public static InstantMessage systemMessage(String content) {
    return new InstantMessage(Instant.now().toEpochMilli(), MessageType.SYSTEM, null, content);
  }

  public static InstantMessage echoMessage(String userId) {
    return new InstantMessage(Instant.now().toEpochMilli(), MessageType.ECHO, userId, null);
  }

  public static InstantMessage userMessage(String userId, String content) {
    return new InstantMessage(Instant.now().toEpochMilli(), MessageType.USER, userId, content);
  }

  @Override
  public String toString() {
    String nowString = LocalDateTime
        .ofInstant(Instant.ofEpochMilli(createTime), ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    if (type == MessageType.USER) {
      return nowString + " " + userId + "> " + content;
    }
    if (type == MessageType.ECHO) {
      return nowString + " ECHO> " + userId;
    }
    if (type == MessageType.SERVER) {
      return nowString + " SERVER> " + content;
    }
    return nowString + " SYSTEM> " + content;
  }

  /**
   * To bytes
   *
   * @param msg <code>InstantMessage</code>
   * @return bytes
   * @throws IOException any Exception while writing
   */
  public static byte[] toBytes(InstantMessage msg) throws IOException {
    // TODO Jackson, Hession, Gson
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);) {
      oos.writeObject(msg);
      return baos.toByteArray();
    }
  }

  /**
   * From Bytes
   *
   * @param bytes bytes
   * @return <code>InstantMessage</code>
   * @throws IOException any Exception while reading
   */
  public static InstantMessage fromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
    // TODO Jackson, Hession, Gson
    try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);) {
      return (InstantMessage) ois.readObject();
    }
  }

}
