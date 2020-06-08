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
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class InstantMessageEncoderTest {

  @Test
  public void testEncode() throws Exception {
    EmbeddedChannel channel = new EmbeddedChannel();
    channel.pipeline().addLast(new InstantMessageEncoder());

    InstantMessage m1 = InstantMessage.systemMessage("say hello");
    InstantMessage m2 = InstantMessage.serverMessage("server message");
    InstantMessage m3 = InstantMessage.echoMessage("c1");
    InstantMessage m4 = InstantMessage.userMessage("c1", "hi, i'm c1");

    channel.writeOutbound(m1);
    channel.writeOutbound(m2);
    channel.writeOutbound(m3);
    channel.writeOutbound(m4);
    InstantMessage cm1 = decode(channel.readOutbound());
    InstantMessage cm2 = decode(channel.readOutbound());
    InstantMessage cm3 = decode(channel.readOutbound());
    InstantMessage cm4 = decode(channel.readOutbound());

    assertMessage(m1, cm1);
    assertMessage(m2, cm2);
    assertMessage(m3, cm3);
    assertMessage(m4, cm4);
  }

  private InstantMessage decode(ByteBuf in) throws Exception {
    int length = in.readInt();
    byte[] body = new byte[length];
    in.readBytes(body);
    return SerializerUtils.fromBytes(body, InstantMessage.class);
  }

  private void assertMessage(InstantMessage m1, InstantMessage cm1) {
    Assert.assertNotNull(cm1);
    Assert.assertEquals(m1.getCreateTime(), cm1.getCreateTime());
    Assert.assertEquals(m1.getType(), cm1.getType());
    Assert.assertEquals(m1.getUserId(), cm1.getUserId());
    Assert.assertEquals(m1.getContent(), cm1.getContent());
  }

}