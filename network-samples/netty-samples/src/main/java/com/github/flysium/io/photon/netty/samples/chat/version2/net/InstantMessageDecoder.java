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

package com.github.flysium.io.photon.netty.samples.chat.version2.net;

import com.github.flysium.io.photon.netty.samples.chat.version2.model.InstantMessage;
import com.github.flysium.io.photon.netty.serializer.SerializerUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Decoder for <code>InstantMessage</code>
 *
 * @author Sven Augustus
 * @version 1.0
 */
// public class InstantMessageDecoder extends ByteToMessageDecoder {
public class InstantMessageDecoder extends LengthFieldBasedFrameDecoder {

  // length ---- 32-bit integer
  public static final int BASE_LENGTH = 4;

//  @Override
//  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//    if (in.readableBytes() <= BASE_LENGTH) {
//      return;
//    }
//    int length = in.readInt();
//    byte[] body = new byte[0];
//    if (length > 0) {
//      body = new byte[length];
//      in.readBytes(body);
//    }
//    out.add(InstantMessage.fromBytes(body));
//  }

  public InstantMessageDecoder() {
    super(Integer.MAX_VALUE, 0, BASE_LENGTH);
  }

  @Override
  protected Object decode(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
    ByteBuf in = (ByteBuf) super.decode(ctx, buf);

    if (in == null) {
      return null;
    }
    if (in.readableBytes() <= BASE_LENGTH) {
      return null;
    }
    int length = in.readInt();
    in.markReaderIndex();

    if (length <= BASE_LENGTH) {
      in.resetReaderIndex();
      return null;
    }
    byte[] body = new byte[length];
    in.readBytes(body);

    return SerializerUtils.fromBytes(body, InstantMessage.class);
  }
}
