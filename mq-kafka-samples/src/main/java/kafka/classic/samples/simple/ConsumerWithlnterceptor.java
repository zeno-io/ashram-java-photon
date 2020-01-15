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

package kafka.classic.samples.simple;

import ch.qos.logback.classic.Level;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import kafka.classic.samples.KafkaTest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

/**
 * 消费者: 自定义消费者拦截器 Consumerlnterceptor
 *
 * @author Sven Augustus
 */
public class ConsumerWithlnterceptor extends KafkaTest {

  public static final String TOPIC = "DemoTopic1";

  public static void main(String[] args) {
    setKafkaLogger(Level.INFO);

    Properties props = new Properties();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092,127.0.0.1:9093");
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "DemoConsumerGroupWithlnterceptor");
    props.put(ConsumerConfig.CLIENT_ID_CONFIG, "DemoConsumerClientWithlnterceptor1");
    // 消费者是否自动提交偏移量
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
    // 自动提交的频率(ms), 默认5000 （5秒）
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
    // 消费者在被认为死亡之前可以与服务器断开连接的时间，默认是 10000（10s）
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000);
    // TODO 消费者拦截器
    //  KafkaConsumer 会在 poll()方法返回之前调用拦截器的 onConsume()方法来对消息进行相应的定制操作，
    //  比如修改返回的消息内容、按照某种规则过滤消息（可能会减少poll() 方法返回的消息的个数〉。
    //   如果 onConsume()方法中抛出异常，那么会被捕获并记录到日志中，但是异常不会再向上传递。
    //  KafkaConsumer会在提交完消费位移之后调用拦截器的 onCommit() 方法，可以使用这个方法来记录跟踪所提交的位移信息，
    //    比如当消费者使用 commitSync()  的无参方法时，我们不知道提交的消费位移的具体细节，而使用拦截器的 onCommit()方法却可以做到这一点。
    props.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, DemoConsumerlnterceptor.class.getName());
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        org.apache.kafka.common.serialization.IntegerDeserializer.class.getName());
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        org.apache.kafka.common.serialization.StringDeserializer.class.getName());

    KafkaConsumer<Integer, String> consumer = new KafkaConsumer<>(props);

    // TODO 订阅主题
    consumer.subscribe(Collections.singletonList(TOPIC));
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

  public static class DemoConsumerlnterceptor implements ConsumerInterceptor<Integer, String> {

    @Override
    public ConsumerRecords<Integer, String> onConsume(ConsumerRecords<Integer, String> records) {
      return records;
    }

    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets) {
      offsets.forEach((tp, offset) -> {
        System.out.println(
            "==== Commit message to topic: (" + tp.topic()
                + ") partition(" + tp.partition()
                + "), " + "offset(" + offset.offset() + ")");
      });
    }

    @Override
    public void close() {
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
  }

}
