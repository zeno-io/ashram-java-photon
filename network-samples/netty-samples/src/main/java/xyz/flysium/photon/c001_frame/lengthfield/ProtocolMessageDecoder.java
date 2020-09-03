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

package xyz.flysium.photon.c001_frame.lengthfield;

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
    in.markReaderIndex();
    short type = in.readShort();
    int length = in.readInt();

    if (in.readableBytes() < length) {
      in.resetReaderIndex();
      return null;
    }
    byte[] body = new byte[length];
    in.readBytes(body);

    return new ProtocolMessage(type, body);
  }

}
