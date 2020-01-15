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
package rocketmq.classic.samples.deadletter;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 测试死信队列 （Dead Letter Queue）
 */
public class DLQTestProducer {

  public static void main(String[] args) throws MQClientException, InterruptedException {
    DefaultMQProducer producer = new DefaultMQProducer("DLQ_TEST_P_1");
    producer.setNamesrvAddr("127.0.0.1:9876");

    //  启动，如果失败，建议增加日志打印
    producer.start();

    try {
      // TODO 标记 key 为 DLQ_KEYS_1111， 让消费者方便模拟重试
      Message msg = new Message("TopicTest3",
          "TagA",
          "DLQ_KEYS_1111",
          "Hello DLQ , I'm coming....".getBytes(RemotingHelper.DEFAULT_CHARSET));
      SendResult sendResult = producer.send(msg);
      System.out.printf("%s%n", sendResult);
    } catch (Exception e) {
      e.printStackTrace();
    }

    producer.shutdown();
    //  如果是Servlet 容器，建议使用 shutdownHook 钩子
    //    Runtime.getRuntime().addShutdownHook(new Thread(producer::shutdown));
  }

}

