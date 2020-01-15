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
package rocketmq.classic.samples.retroactive;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageQueue;

/**
 * 消息回溯（消息重新消费）(Message Retroactive): 测试 单个 MessageQueue 基于 指定的 offset 消费
 */
public class PullConsumerRetroactiveTest {

  public static void main(String[] args) throws MQClientException {
    DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("please_rename_unique_group_name_5");
    consumer.setNamesrvAddr("127.0.0.1:9876");
    consumer.start();

    try {
      MessageQueue mq = new MessageQueue();
      mq.setQueueId(0);
      mq.setTopic("TopicTest");
      mq.setBrokerName("RaftNode00");

      long offset = 26;

      long beginTime = System.currentTimeMillis();
      PullResult pullResult = consumer.pullBlockIfNotFound(mq, null, offset, 32);
      System.out.printf("%s%n", System.currentTimeMillis() - beginTime);
      System.out.printf("%s%n", pullResult);
    } catch (Exception e) {
      e.printStackTrace();
    }

    consumer.shutdown();
  }
}
