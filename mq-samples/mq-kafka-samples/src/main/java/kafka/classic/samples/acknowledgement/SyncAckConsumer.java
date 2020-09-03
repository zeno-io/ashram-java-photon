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

package kafka.classic.samples.acknowledgement;

import ch.qos.logback.classic.Level;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import kafka.classic.samples.KafkaTest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消费者：同步提交 Offset
 *
 * @author Sven Augustus
 */
public class SyncAckConsumer extends KafkaTest {

  public static final String TOPIC = "DemoTopic1";
  private static final Logger LOGGER = LoggerFactory.getLogger(SyncAckConsumer.class);

  public static void main(String[] args) {
    setKafkaLogger(Level.INFO);

    Properties props = new Properties();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092,127.0.0.1:9093");
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "DemoConsumerGroup2");
    props.put(ConsumerConfig.CLIENT_ID_CONFIG, "DemoConsumerClient2");
    // TODO 消费者是否自动提交偏移量
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        org.apache.kafka.common.serialization.IntegerDeserializer.class.getName());
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        org.apache.kafka.common.serialization.StringDeserializer.class.getName());

    // 线程安全问题
    // 在同一个群组里，我们无法让一个线程运行多个消费者，也无法让多个线程安全地共享一个消费者。
    // 按照规则，一个消费者使用一个线程。如果 要在同一个消费者群组里运行多个消费者，需要让每个消费者运行在自己 的线程里。
    // 最好是把消费者的逻辑封装在自己的对象里，然后使用 Java 的 ExecutorService 启动多个线程，使每个消费者运行在自己的线程上。
    KafkaConsumer<Integer, String> consumer = new KafkaConsumer<>(props);
    // TODO 订阅主题
    consumer.subscribe(Collections.singletonList(TOPIC));
    // consumer.subscribe(Collections.singletonList("test.*"));
    // TODO PUll 拉取消息，可设置超时时间
    for (int i = 0; i < 3; i++) {
      ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofSeconds(3));
      System.out.println("====== Received messages size: " + records.count());
      for (ConsumerRecord<Integer, String> record : records) {
        System.out.println(
            "==== Received message: (" + record.key() + ", " + record.value()
                + ") at topic(" + record.topic() + "), partition(" + record.partition()
                + "), offset(" + record.offset() + "), timestamp(" + record.timestamp() + ")");
      }
      try {
        if (records.count() > 0) {
          // TODO 同步提交当前批次最新的偏移量。
          //  使用 commitSync() 方法会一直重试，直到提交成功或发生无法恢复的错误。
          consumer.commitSync();
        }
      } catch (Exception e) {
        // 只要没有发生不可恢复的错误，commitSync() 方法会一直尝试直至提交成功。
        // 如果提交失败，我们也只能把异常记录到错误日志里。
        LOGGER.error("commit failed", e);
      }
    }

    try {
      TimeUnit.SECONDS.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        consumer.close();
      }
    });
  }

}
