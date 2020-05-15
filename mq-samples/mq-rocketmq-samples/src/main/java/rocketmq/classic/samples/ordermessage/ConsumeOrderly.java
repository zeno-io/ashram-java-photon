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
package rocketmq.classic.samples.ordermessage;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * PUSH 消费： 顺序消费
 */
public class ConsumeOrderly {

  public static void main(String[] args) throws MQClientException {
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name_3");
    consumer.setNamesrvAddr("127.0.0.1:9876");
    // 消费进度策略： CONSUME_FROM_LAST_OFFSET : 忽略 Broker 存在的历史信息，从上次停下的地方继续消费
    //              CONSUME_FROM_FIRST_OFFSET ：每次都是从 Broker 最开始的消息开始消费
    //              CONSUME_FROM_TIMESTAMP： 从指定时间戳的消息之后开始消费
    consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
    // FIXME 线程数: 消费者使用一个 ThreadPoolExecutor 来处理内部的消费，
    //   因此您可以通过设置setConsumeThreadMin或setConsumeThreadMax来更改它。
    consumer.setConsumeThreadMin(2);
    consumer.setConsumeThreadMax(4);

    consumer.subscribe("TopicTestjjj", "TagA || TagC || TagD");

    consumer.registerMessageListener(new MessageListenerOrderly() {
      AtomicLong consumeTimes = new AtomicLong(0);

      @Override
      public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs,
          ConsumeOrderlyContext context) {
        //  许多情况可能导致重复，例如：E
        //    生产者重新发送消息（i.e, in case of FLUSH_SLAVE_TIMEOUT）
        //    消费者关闭时未将offsets 更新到 Broker
        //    因此，如果您的应用程序不能容忍重复，那么您可能需要做一些外部工作来处理这个问题。例如，您可以检查DB的主键。

        context.setAutoCommit(true);
        System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
        this.consumeTimes.incrementAndGet();
        if ((this.consumeTimes.get() % 2) == 0) {
          return ConsumeOrderlyStatus.SUCCESS;
        } else if ((this.consumeTimes.get() % 3) == 0) {
          return ConsumeOrderlyStatus.ROLLBACK;
        } else if ((this.consumeTimes.get() % 4) == 0) {
          return ConsumeOrderlyStatus.COMMIT;
        } else if ((this.consumeTimes.get() % 5) == 0) {
          // FIXME: 消费者将锁定每个MessageQueue，以确保每个消息被一个按顺序使用。
          //  这将导致性能损失，但是当您关心消息的顺序时，它就很有用了。
          //  不建议抛出异常，您可以返回ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT代替。
          context.setSuspendCurrentQueueTimeMillis(3000);
          return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
        }

        return ConsumeOrderlyStatus.SUCCESS;
      }
    });
    //  启动，如果失败，建议增加日志打印
    consumer.start();
    System.out.printf("Consumer Started.%n");
  }

}
