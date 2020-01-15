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
package rocketmq.classic.samples.transaction;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 生产者：支持分布式事务，指的消息的生产与本地事务的最终一致性
 */
public class TransactionProducer {

  public static void main(String[] args) throws MQClientException, InterruptedException {
    TransactionMQProducer producer = new TransactionMQProducer("please_rename_unique_group_name");
    producer.setNamesrvAddr("127.0.0.1:9876");
    // FIXME 设置事务回查线程池
    producer.setExecutorService(new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS,
        new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
      @Override
      public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName("client-transaction-msg-check-thread");
        return thread;
      }
    }, new CallerRunsPolicy()));

    // FIXME 自己实现TransactionListener接口，并实现
    //  executeLocalTransaction 方法 (执行本地事务的，一般就是操作DB相关内容)和
    //  checkLocalTransaction方法 (用来提供给broker进行会查本地事务消息的，把本地事务执行的结果存储到redis或者DB中都可以，为回查做数据准备)。
    producer.setTransactionListener(new TransactionListenerImpl());

    // TODO 启动，如果失败，建议增加日志打印
    producer.start();

    String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
    for (int i = 0; i < 10; i++) {
      try {
        Message msg =
            new Message("TopicTest1234", tags[i % tags.length], "KEY" + i,
                ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
        SendResult sendResult = producer.sendMessageInTransaction(msg, null);
        System.out.printf("%s%n", sendResult);

        Thread.sleep(10);
      } catch (MQClientException | UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }

    for (int i = 0; i < 100000; i++) {
      Thread.sleep(1000);
    }

    producer.shutdown();
    // FIXME 如果是Servlet 容器，建议使用 shutdownHook 钩子
    //    Runtime.getRuntime().addShutdownHook(new Thread(producer::shutdown));
  }
}
