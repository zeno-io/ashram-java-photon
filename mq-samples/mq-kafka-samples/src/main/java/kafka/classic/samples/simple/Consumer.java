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

package kafka.classic.samples.simple;

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

/**
 * 消费者
 *
 * @author Sven Augustus
 */
public class Consumer extends KafkaTest {

  public static final String TOPIC = "DemoTopic1";

  public static void main(String[] args) {
    setKafkaLogger(Level.INFO);

    Properties props = new Properties();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092,127.0.0.1:9093");
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "DemoConsumerGroup0");
    props.put(ConsumerConfig.CLIENT_ID_CONFIG, "DemoConsumerClient1");
    // 消费者是否自动提交偏移量
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
    // 自动提交的频率(ms), 默认5000 （5秒）
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
    // 消费者在被认为死亡之前可以与服务器断开连接的时间，默认是 10000（10s）
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000);
    // offset偏移量规则设置：
    //  指定了消费者在读取一个没有偏移量的分区或者偏移量无效的情况下（因消费者长时间失效，包含偏移量的记录已经过时并被删除）该作何处理, 默认值是 latest
    //  (1)、latest：automatically reset the offset to the latest offset
    //  当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据
    //  (2)、earliest： automatically reset the offset to the earliest offset
    //  当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
    //  (3)、none： throw exception to the consumer if no previous offset is found for the consumer's group
    //  当各分区下有已提交的offset时，从提交的offset开始消费； 只要有一个分区不存在已提交的offset，则抛出异常
    props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        org.apache.kafka.common.serialization.IntegerDeserializer.class.getName());
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        org.apache.kafka.common.serialization.StringDeserializer.class.getName());

    // FIXME 线程安全问题
    //  在同一个群组里，我们无法让一个线程运行多个消费者，也无法让多个线程安全地共享一个消费者。
    //  按照规则，一个消费者使用一个线程。如果 要在同一个消费者群组里运行多个消费者，需要让每个消费者运行在自己 的线程里。
    //  最好是把消费者的逻辑封装在自己的对象里，然后使用 Java 的 ExecutorService 启动多个线程，使每个消费者运行在自己的线程上。
    KafkaConsumer<Integer, String> consumer = new KafkaConsumer<>(props);

    // TODO 订阅主题
    consumer.subscribe(Collections.singletonList(TOPIC));
    // consumer.subscribe(Collections.singletonList("test.*"));
    // TODO 直接订阅某些主题特定分区, 注意 assign() 不具备自动均衡的功能
//    List<TopicPartition> partitions = new ArrayList<>();
//    List<PartitionInfo> partitioninfos = consumer.partitionsFor(TOPIC);
//    if (partitioninfos != null) {
//      for (PartitionInfo partitionInfo : partitioninfos) {
//        partitions.add(new TopicPartition(partitionInfo.topic(), partitionInfo.partition()));
//      }
//    }
//    consumer.assign(partitions);
    // TODO 取消订阅
    // consumer.unsubscribe();
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
