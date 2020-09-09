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
package rocketmq.classic.samples.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.RPCHook;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * ACL 安全控制
 */
public class AclClient {

  private static final Map<MessageQueue, Long> OFFSET_TABLE = new HashMap<MessageQueue, Long>();

  private static final String ACL_ACCESS_KEY = "RocketMQ";
  private static final String ACL_SECRET_KEY = "1234567";

  public static void main(String[] args) throws MQClientException, InterruptedException {
    producer();
    pushConsumer();
    pullConsumer();
  }

  // FIXME ACL 通过定义 RPC Hook完成安全控制
  static RPCHook getAclRPCHook() {
    return new AclClientRPCHook(new SessionCredentials(ACL_ACCESS_KEY, ACL_SECRET_KEY));
  }

  public static void producer() throws MQClientException {
    DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName", getAclRPCHook());
    producer.setNamesrvAddr("127.0.0.1:9876");
    producer.start();

    for (int i = 0; i < 128; i++) {
      try {
        {
          Message msg = new Message("TopicTest",
              "TagA",
              "OrderID188",
              "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
          SendResult sendResult = producer.send(msg);
          System.out.printf("%s%n", sendResult);
        }

      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    producer.shutdown();
  }

  public static void pushConsumer() throws MQClientException {

    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name_5",
        getAclRPCHook(), new AllocateMessageQueueAveragely());
    consumer.setNamesrvAddr("127.0.0.1:9876");
    consumer.subscribe("TopicTest", "*");
    consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
    // Wrong time format 2017_0422_221800
    consumer.setConsumeTimestamp("20180422221800");
    consumer.registerMessageListener(new MessageListenerConcurrently() {

      @Override
      public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
          ConsumeConcurrentlyContext context) {
        System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
        printBody(msgs);
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
      }
    });
    consumer.start();
    System.out.printf("Consumer Started.%n");
  }

  public static void pullConsumer() throws MQClientException {
    DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("please_rename_unique_group_name_6",
        getAclRPCHook());
    consumer.setNamesrvAddr("127.0.0.1:9876");
    consumer.start();

    Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues("TopicTest");
    for (MessageQueue mq : mqs) {
      System.out.printf("Consume from the queue: %s%n", mq);
      SINGLE_MQ:
      while (true) {
        try {
          PullResult pullResult =
              consumer.pullBlockIfNotFound(mq, null, getMessageQueueOffset(mq), 32);
          System.out.printf("%s%n", pullResult);
          putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
          printBody(pullResult);
          switch (pullResult.getPullStatus()) {
            case FOUND:
              break;
            case NO_MATCHED_MSG:
              break;
            case NO_NEW_MSG:
              break SINGLE_MQ;
            case OFFSET_ILLEGAL:
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
  }

  private static void printBody(PullResult pullResult) {
    printBody(pullResult.getMsgFoundList());
  }

  private static void printBody(List<MessageExt> msg) {
    if (msg == null || msg.size() == 0) {
      return;
    }
    for (MessageExt m : msg) {
      if (m != null) {
        System.out.printf("msgId : %s  body : %s  \n\r", m.getMsgId(), new String(m.getBody()));
      }
    }
  }

  private static long getMessageQueueOffset(MessageQueue mq) {
    Long offset = OFFSET_TABLE.get(mq);
    if (offset != null) {
      return offset;
    }

    return 0;
  }

  private static void putMessageQueueOffset(MessageQueue mq, long offset) {
    OFFSET_TABLE.put(mq, offset);
  }


}
