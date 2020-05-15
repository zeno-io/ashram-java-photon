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

import java.util.HashMap;
import java.util.Map;
import org.apache.rocketmq.client.consumer.MQPullConsumer;
import org.apache.rocketmq.client.consumer.MQPullConsumerScheduleService;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.PullTaskCallback;
import org.apache.rocketmq.client.consumer.PullTaskContext;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

/**
 * Pull 消费者，采用异步定期方式
 */
public class PullScheduleService {

  public static void main(String[] args) throws MQClientException {
    final MQPullConsumerScheduleService scheduleService = new MQPullConsumerScheduleService(
        "GroupName1");
    if (scheduleService.getDefaultMQPullConsumer() != null) {
      scheduleService.getDefaultMQPullConsumer().setNamesrvAddr("127.0.0.1:9876");
      // 消费模式：集群消费，还是广播消费
      scheduleService.getDefaultMQPullConsumer().setMessageModel(MessageModel.CLUSTERING);
      // TODO 更多参数，可以参考 PullConsumer 或者 客户端API: DefaultMQPullConsumer
    }

    // FIXME 注册 PULL 回调函数, 可以参考 DefaultMQPushConsumerImpl#pullMessage 里面 PullCallback 的写法
    scheduleService.registerPullTaskCallback("TopicTest", new PullTaskCallback() {

      @Override
      public void doPullTask(MessageQueue mq, PullTaskContext context) {
        MQPullConsumer consumer = context.getPullConsumer();
        try {
          // 读取消费进度
          long offset = getMessageQueueOffset(consumer, mq);

          PullResult pullResult = consumer.pull(mq, "*", offset, 32);
          System.out.printf("%s%n", offset + "\t" + mq + "\t" + pullResult);

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
            case OFFSET_ILLEGAL:
              // TODO 消息偏移量要么过大，要么过小
              break;
            default:
              break;
          }
          // 保存消费进度
          putMessageQueueOffset(consumer, mq, pullResult.getNextBeginOffset());

          // 设置下次 PULL 的延迟时间，单位毫秒
          context.setPullNextDelayTimeMillis(100);

        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

    scheduleService.start();
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
