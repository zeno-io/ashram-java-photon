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
package rocketmq.classic.samples.deadletter;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 测试死信队列 （Dead Letter Queue）
 */
public class DLQTestConsumer {

  public static void main(String[] args) throws InterruptedException, MQClientException {
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("DLQ_TEST_C_1");
    consumer.setNamesrvAddr("127.0.0.1:9876");
    // FIXME 重试最大次数，默认16次，也就是最多可以消费 maxReconsumeTimes + 1 次
    consumer.setMaxReconsumeTimes(3);

    consumer.subscribe("TopicTest3", "*");

    consumer.registerMessageListener(new MessageListenerConcurrently() {

      @Override
      public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
          ConsumeConcurrentlyContext context) {
        System.out
            .printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
        // TODO 当 key 为 DLQ_KEYS_1111， 标记重试
        //     重试的消息在延迟的某个时间点（默认是10秒，业务可设置）后，再次投递到这个ConsumerGroup （其实从真实主题 %RETRY%DLQ_TEST_C_1 中投递）。
        //     而如果一直这样重复消费都持续失败到一定次数（默认16次），就会投递到DLQ死信队列，不再投递消息。 （%DLQ%DLQ_TEST_C_1 ）
        //     消费者可以设置重复消费最大次数 DefaultMQPushConsumer#maxReconsumeTimes 或 DefaultMQPullConsumer#maxReconsumeTimes
        if (msgs.stream()
            .anyMatch(messageExt -> StringUtils.equals("DLQ_KEYS_1111", messageExt.getKeys()))) {
          System.out.println("RECONSUME_LATER");
          return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        System.out.println("CONSUME_SUCCESS");
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
      }
    });
    //  启动，如果失败，建议增加日志打印
    consumer.start();
    System.out.printf("Consumer Started.%n");

  }
}
