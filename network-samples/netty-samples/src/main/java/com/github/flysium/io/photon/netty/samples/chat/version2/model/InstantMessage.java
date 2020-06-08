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

package com.github.flysium.io.photon.netty.samples.chat.version2.model;

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
