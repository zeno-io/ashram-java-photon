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

package com.github.flysium.io.photon.netty.c002_pojo;

/**
 * Message
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class ObjectMessage implements java.io.Serializable {

  private static final long serialVersionUID = 3318923566097676372L;
  private int type;

  private String origin;

  private String body;

  public ObjectMessage() {
  }

  public ObjectMessage(int type, String origin, String body) {
    this.type = type;
    this.origin = origin;
    this.body = body;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getOrigin() {
    return origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public String toString() {
    return "ObjectMessage{" +
        "type=" + type +
        ", origin='" + origin + '\'' +
        ", body='" + body + '\'' +
        '}';
  }

}
