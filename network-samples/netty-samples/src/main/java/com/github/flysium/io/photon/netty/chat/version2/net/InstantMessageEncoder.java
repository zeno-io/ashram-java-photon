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

package com.github.flysium.io.photon.netty.chat.version2.net;

import com.github.flysium.io.photon.netty.chat.version2.model.InstantMessage;
import com.github.flysium.io.photon.netty.serializer.SerializerUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Encoder for <code>InstantMessage</code>
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class InstantMessageEncoder extends MessageToByteEncoder<InstantMessage> {

  @Override
  protected void encode(ChannelHandlerContext ctx, InstantMessage msg, ByteBuf out)
      throws Exception {
    byte[] body = SerializerUtils.toBytes(msg);
    int length = body.length;
    out.writeInt(length);
    out.writeBytes(body);
  }

}