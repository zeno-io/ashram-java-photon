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

package xyz.flysium.photon.rpc.remoting.transport;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.flysium.photon.rpc.remoting.portocol.RpcMessage;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class RpcMessageDecoder extends LengthFieldBasedFrameDecoder {

  // 2 + 8
  public static final int LENGTH_FIELD_OFFSET = 10;
  public static final int LENGTH_FIELD_LENGTH = 4;
  public static final int BASE_LENGTH = LENGTH_FIELD_OFFSET + LENGTH_FIELD_LENGTH;
  private Logger logger = LoggerFactory.getLogger(getClass());

  public RpcMessageDecoder() {
    super(4096, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
  }

  @Override
  protected Object decode(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
    ByteBuf in = (ByteBuf) super.decode(ctx, buf);
    if (in == null) {
      logger.info("------decode remaining 0----->" + in);
      return null;
    }
//    if (in.readableBytes() <= BASE_LENGTH) {
//      logger.info("------decode remaining 1----->" + in);
//      return null;
//    }
//    in.markReaderIndex();
    short type = in.readShort();
    long mId = in.readLong();
    int length = in.readInt();

//    if (in.readableBytes() < length) {
//      logger.info("------decode remaining 2----->" + in);
//      in.resetReaderIndex();
//      return null;
//    }
    byte[] body = new byte[length];
    in.readBytes(body);

    return new RpcMessage(type, mId, body);
  }

}
