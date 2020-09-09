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
