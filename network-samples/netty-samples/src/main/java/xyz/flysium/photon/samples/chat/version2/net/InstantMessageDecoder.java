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

package xyz.flysium.photon.samples.chat.version2.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import xyz.flysium.photon.samples.chat.version2.model.InstantMessage;
import xyz.flysium.photon.serializer.SerializerUtils;

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
    in.markReaderIndex();
    int length = in.readInt();

    if (in.readableBytes() < length) {
      in.resetReaderIndex();
      return null;
    }
    byte[] body = new byte[length];
    in.readBytes(body);

    return SerializerUtils.fromBytes(body, InstantMessage.class);
  }
}
