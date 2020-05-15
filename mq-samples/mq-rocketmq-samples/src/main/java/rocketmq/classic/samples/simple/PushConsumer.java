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
package rocketmq.classic.samples.simple;

import java.util.List;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * PUSH 消费： 并行消费
 */
public class PushConsumer {

  public static void main(String[] args) throws InterruptedException, MQClientException {
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("CID_JODIE_1");
    consumer.setNamesrvAddr("127.0.0.1:9876");
    // 消费进度策略： CONSUME_FROM_LAST_OFFSET : 忽略 Broker 存在的历史信息，从上次停下的地方继续消费
    //              CONSUME_FROM_FIRST_OFFSET ：每次都是从 Broker 最开始的消息开始消费
    //              CONSUME_FROM_TIMESTAMP： 从指定时间戳的消息之后开始消费
    consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
    //wrong time format 2017_0422_221800
    consumer.setConsumeTimestamp("20181109221800");
    // FIXME 流量控制，线程数: 消费者使用一个 ThreadPoolExecutor 来处理内部的消费，
    //   因此您可以通过设置setConsumeThreadMin或setConsumeThreadMax来更改它。
    consumer.setConsumeThreadMin(2);
    consumer.setConsumeThreadMax(4);

    consumer.subscribe("TopicTest", "*");

    consumer.registerMessageListener(new MessageListenerConcurrently() {

      @Override
      public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
          ConsumeConcurrentlyContext context) {
        // FIXME 许多情况可能导致重复，例如：E
        //    生产者重新发送消息（i.e, in case of FLUSH_SLAVE_TIMEOUT）
        //    消费者关闭时未将offsets 更新到 Broker
        //    因此，如果您的应用程序不能容忍重复，那么您可能需要做一些外部工作来处理这个问题。例如，您可以检查DB的主键。
        try {
          System.out
              .printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
          return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        } catch (RuntimeException e) {
          // FIXME: 并行（Concurrently）
          //  顾名思义，消费者将同时使用这些消息。为良好的性能，推荐使用此方法。
          //  不建议抛出异常，您可以返回ConsumeConcurrentlyStatus.RECONSUME_LATER代替。
          return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
      }
    });
    // TODO 启动，如果失败，建议增加日志打印
    consumer.start();
    System.out.printf("Consumer Started.%n");

  }
}
