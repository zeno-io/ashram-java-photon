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
package rocketmq.classic.samples.scheduledmessage;

import java.io.UnsupportedEncodingException;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 生产者：延迟消息
 */
public class ProduceDelayMessage {

   // TODO Broker端 可以设置 messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
  public static final int DELAY_LEVEL = 3;

  public static void main(String[] args) throws UnsupportedEncodingException {
    try {
      DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
      producer.setNamesrvAddr("127.0.0.1:9876");
      producer.start();

      for (int i = 0; i < 100; i++) {
        Message msg = new Message("TopicTestDDD",
            "TagA",
            "OrderID188",
            "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));

        // TODO 这个消息将在 10s 后，消费者才会获取到
        msg.setDelayTimeLevel(DELAY_LEVEL);

        SendResult sendResult = producer.send(msg);
        System.out.printf("%s%n", sendResult);
      }

      producer.shutdown();
    } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
