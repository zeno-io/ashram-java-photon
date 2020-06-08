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

package com.github.flysium.io.photon.netty.c001_frame.lengthfield;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Decoder for <code>ProtocolMessage</code>
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class ProtocolMessageDecoder extends LengthFieldBasedFrameDecoder {

  public static final int LENGTH_FIELD_OFFSET = 2;
  public static final int LENGTH_FIELD_LENGTH = 4;
  public static final int BASE_LENGTH = LENGTH_FIELD_OFFSET + LENGTH_FIELD_LENGTH;

  public ProtocolMessageDecoder() {
    super(1024, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
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
    short type = in.readShort();
    int length = in.readInt();
    in.markReaderIndex();

    if (length <= BASE_LENGTH) {
      in.resetReaderIndex();
      return null;
    }
    byte[] body = new byte[length];
    in.readBytes(body);

    return new ProtocolMessage(type, body);
  }

}
