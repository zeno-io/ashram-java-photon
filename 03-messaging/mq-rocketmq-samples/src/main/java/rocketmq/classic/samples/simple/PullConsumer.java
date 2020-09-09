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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.MQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

/**
 * PULL 消费 (4.7开始废弃，改用 LITE PULL )
 */
public class PullConsumer {

  private static final Map<MessageQueue, Long> OFFSE_TABLE = new HashMap<MessageQueue, Long>();

  public static void main(String[] args) throws MQClientException {
    DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("please_rename_unique_group_name_5");
    consumer.setNamesrvAddr("127.0.0.1:9876");
    // 消费模式：集群消费，还是广播消费
    consumer.setMessageModel(MessageModel.CLUSTERING);
    // 连接 broker 拉取消息的超时时间， 默认 10 s
    consumer.setConsumerPullTimeoutMillis(1000 * 10);
    // FIXME 如果一直这样重复消费都持续失败到一定次数（默认16次），就会投递到DLQ死信队列。
    consumer.setMaxReconsumeTimes(16);
    // TODO VipChannel 默认为true，占用10909端口，此时需要开放10909端口，否则会报 ：connect to <：10909> failed异常，可以直接设置为false
    consumer.setVipChannelEnabled(true);
    // FIXME 消费者分配队列的负载均衡算法
    consumer.setAllocateMessageQueueStrategy(new AllocateMessageQueueAveragely());
    // TODO 启动，如果失败，建议增加日志打印
    consumer.start();

    // FIXME 需要定制大量代码，一般不推荐。如果非要定制，请参考 DefaultMQPushConsumerImpl 的实现。
    Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues("TopicTest");
    for (MessageQueue mq : mqs) {
      System.out.printf("Consume from the queue: %s%n", mq);
      SINGLE_MQ:
      while (true) {
        try {
          // 读取消费进度
          long offset = getMessageQueueOffset(consumer, mq);

          // TODO 同步阻塞式拉取，消息未到达默认是阻塞10秒，consumerPullTimeoutMillis;
          PullResult pullResult = consumer.pullBlockIfNotFound(mq, "*",
              offset,
              // 每次拉取消息的最大数量
              32);
          System.out.printf("%s%n", pullResult);

          // 保存消费进度
          putMessageQueueOffset(consumer, mq, pullResult.getNextBeginOffset());

          switch (pullResult.getPullStatus()) {
            case FOUND:
              // FIXME 找到消息，执行业务逻辑处理
              //  在 Pull 模式，没有 ACK 的机制，而是存储消费进度，如果失败，可以考虑本地重试，或者不要 updateConsumeOffset （这种情况可能阻塞处理性能）
              System.out.printf("consume msg size: %s%n", pullResult.getMsgFoundList().size());
              break;
            case NO_MATCHED_MSG:
              // TODO 消息过滤时，没有匹配结果
              break;
            case NO_NEW_MSG:
              // TODO 没有消息
              break SINGLE_MQ; // 跳出 SINGLE_MQ 所在的语句：while (true)
            case OFFSET_ILLEGAL:
              // TODO 消息偏移量要么过大，要么过小
              break;
            default:
              break;
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    consumer.shutdown();
    // FIXME 如果是Servlet 容器，建议使用 shutdownHook 钩子
    //    Runtime.getRuntime().addShutdownHook(new Thread(consumer::shutdown));
    //    另外注意保存 Offset
  }

  private static final Map<MessageQueue, Long> OFFSET_TABLE = new HashMap<MessageQueue, Long>();

  // 读取消费进度
  private static long getMessageQueueOffset(MQPullConsumer consumer, MessageQueue mq)
      throws MQClientException {
    // FIXME 使用 Broker 的消费进度, 结合本地内存一起使用
    //  注意：在Consumer被重启后仍然有可能滞后, 持久化参数 由 consumer#persistConsumerOffsetInterval 控制
    long offset = consumer.fetchConsumeOffset(mq, true);
    return Math.max(offset, OFFSET_TABLE.getOrDefault(mq, 0L));
  }

  // 保存消费进度
  private static void putMessageQueueOffset(MQPullConsumer consumer, MessageQueue mq,
      long offset) throws MQClientException {
    OFFSET_TABLE.put(mq, offset);
    // FIXME 使用 Broker 的消费进度, 结合本地内存一起使用
    //  注意：这里并不会立刻 连接 Broker 存储消费进度。
    consumer.updateConsumeOffset(mq, offset);
  }

}
