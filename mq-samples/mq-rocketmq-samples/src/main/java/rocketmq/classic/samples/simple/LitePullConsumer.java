/*
 * Copyright 2020 SvenAugustus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rocketmq.classic.samples.simple;

import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

/**
 * LITE PULL 消费
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class LitePullConsumer {

  public static void main(String[] args) throws MQClientException {
    DefaultLitePullConsumer consumer = new DefaultLitePullConsumer(
        "please_rename_unique_group_name_10");
    consumer.setNamesrvAddr("127.0.0.1:9876");
    // 消费模式：集群消费，还是广播消费
    consumer.setMessageModel(MessageModel.CLUSTERING);
    // 连接 broker 拉取消息的超时时间， 默认 10 s
    consumer.setConsumerPullTimeoutMillis(1000 * 10);
    // 要消费的topic，可使用tag进行简单过滤
    consumer.subscribe("TopicTest", "*");
    // 一次最大消费的条数
    consumer.setPullBatchSize(100);
    // 无消息时，最大阻塞时间。默认5000 单位ms
    consumer.setPollTimeoutMillis(5000);
    // TODO VipChannel 默认为true，占用10909端口，此时需要开放10909端口，否则会报 ：connect to <：10909> failed异常，可以直接设置为false
    consumer.setVipChannelEnabled(true);
    // FIXME 消费者分配队列的负载均衡算法
    consumer.setAllocateMessageQueueStrategy(new AllocateMessageQueueAveragely());
    // TODO 启动，如果失败，建议增加日志打印
    consumer.start();
    boolean runFlag = true;
    while (runFlag) {
      try {
        // FIXME 拉取消息，无消息时会阻塞 -> setPollTimeoutMillis
        List<MessageExt> msgFoundList = consumer.poll();
        if (CollectionUtils.isEmpty(msgFoundList)) {
          continue;
        }
        // FIXME 找到消息，执行业务逻辑处理
        System.out.printf("consume msg size: %s%n", msgFoundList.size());
        msgFoundList.forEach(msg -> System.out.println(new String(msg.getBody())));

        // 同步消费位置。不执行该方法，应用重启会存在重复消费。
        consumer.commitSync();

      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    consumer.shutdown();
    // FIXME 如果是Servlet 容器，建议使用 shutdownHook 钩子
    //    Runtime.getRuntime().addShutdownHook(new Thread(consumer::shutdown));
  }

}
