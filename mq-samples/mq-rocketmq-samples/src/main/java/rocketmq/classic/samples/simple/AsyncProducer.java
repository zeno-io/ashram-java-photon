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

import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 生产者：异步发送
 */
public class AsyncProducer {

  public static void main(
      String[] args) throws MQClientException, InterruptedException, UnsupportedEncodingException {

    DefaultMQProducer producer = new DefaultMQProducer("Jodie_Daily_test");
    producer.setNamesrvAddr("127.0.0.1:9876");
    // FIXME 发送端的 send 方法本身支持内部重试，重试逻辑如下：
    //  a)至多重试2次；
    //  b)如果发送失败，则轮转到下一个broker；
    //  c)这个方法的总耗时不超过sendMsgTimeout 设置的值，默认 3s，超过时间不在重试。
//    producer.setRetryTimesWhenSendFailed(2);
    producer.setRetryTimesWhenSendAsyncFailed(2);
    producer.setSendMsgTimeout(3000);

    // TODO 启动，如果失败，建议增加日志打印
    producer.start();

    int messageCount = 100;
    // FIXME 这里增加 CountDownLatch 发令枪 为了让主线程等 异步回调完成再结束， 生产环境不需要。
    final CountDownLatch countDownLatch = new CountDownLatch(messageCount);
    for (int i = 0; i < messageCount; i++) {
      try {
        final int index = i;
        Message msg = new Message("Jodie_topic_1023",
            "TagA",
            "OrderID188",
            "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));

        // FIXME 这里注意发送如果失败，会自动重试，重试次数：retryTimesWhenSendAsyncFailed
        producer.send(msg, new SendCallback() {
          @Override
          public void onSuccess(SendResult sendResult) {
            countDownLatch.countDown();
            System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
          }

          @Override
          public void onException(Throwable e) {
            countDownLatch.countDown();
            System.out.printf("%-10d Exception %s %n", index, e);
            e.printStackTrace();
          }
        });

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    countDownLatch.await(5, TimeUnit.SECONDS);

    producer.shutdown();
    // FIXME 如果是Servlet 容器，建议使用 shutdownHook 钩子
    //    Runtime.getRuntime().addShutdownHook(new Thread(producer::shutdown));
  }
}
