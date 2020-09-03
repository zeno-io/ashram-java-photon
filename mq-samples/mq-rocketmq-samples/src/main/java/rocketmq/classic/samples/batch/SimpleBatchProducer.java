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

package rocketmq.classic.samples.batch;

import java.util.ArrayList;
import java.util.List;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * 生产者：批量发送消息
 */
public class SimpleBatchProducer {

  public static void main(String[] args) throws Exception {
    DefaultMQProducer producer = new DefaultMQProducer("BatchProducerGroupName");
    producer.setNamesrvAddr("127.0.0.1:9876");
    producer.start();

    //If you just send messages of no more than 1MiB at a time, it is easy to use batch
    //Messages of the same batch should have: same topic, same waitStoreMsgOK and no schedule support
    String topic = "BatchTest";
    List<Message> messages = new ArrayList<>();
    messages.add(new Message(topic, "Tag", "OrderID001", "Hello world 0".getBytes()));
    messages.add(new Message(topic, "Tag", "OrderID002", "Hello world 1".getBytes()));
    messages.add(new Message(topic, "Tag", "OrderID003", "Hello world 2".getBytes()));

    // FIXME 建议在小批量的消息发送，采用此类方式
    producer.send(messages);
  }
}
