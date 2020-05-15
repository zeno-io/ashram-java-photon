/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
