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

package kafka.classic.samples.acknowledgement;

import ch.qos.logback.classic.Level;
import java.util.Properties;
import java.util.concurrent.Future;
import kafka.classic.samples.KafkaTest;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * 生产者
 *
 * @author Sven Augustus
 */
public class Producer extends KafkaTest {

  public static final String TOPIC = "DemoTopic1";

  public static void main(String[] args) {
    setKafkaLogger(Level.INFO);

    Properties props = new Properties();
    // Kafka 服务器地址
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092,127.0.0.1:9093");
    props.put(ProducerConfig.CLIENT_ID_CONFIG, "DemoProducerClient10");
    // TODO acks 参数指定了必须要有多少个分区副本收到消息，生产者才会认为消息写入是成功的。默认 acks=1
    //  acks=0 把消息发送到 kafka就认为发送成功
    //  acks=1 把消息发送到 kafka leader分区，并且写入磁盘就认为发送成功
    //  acks=all 把消息发送到 kafka leader分区，并且leader分区的副本follower对消息进行了同步就任务发送成功
    //  当采用事务机制，acks 必须设置为 all
    props.put(ProducerConfig.ACKS_CONFIG, "1");
    // TODO 生产者可以重发消息的次数，如果达到这个次数，生产者会放弃重试并返回错误, 默认 0
    //  默认情况下，生产者会在每次重试之间等待 100ms，不过可以通过 retry.backoff.ms 参数来改变这个时间间隔。
    props.put(ProducerConfig.RETRIES_CONFIG, "1");
    props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, "50");
    // TODO 控制生产者发送的请求大小。它可以指能发送的单个消息的最大值，也可以指单个请求里所有消息总的大小，默认 1048576 （1MB）
    //  broker 对可接收的消息最大值也有自己的限制（message.max.bytes），所以两边的配置最好可以匹配，避免生产者发送的消息被 broker 拒绝。
    props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, "1000012");
    // 压缩消息，支持四种类型，分别为：none、lz4、gzip、snappy，默认为none。
    // snappy 压缩算法由 Google 发明，它占用较少的 CPU，却能提供较好的性能和相当可观的压缩比，如果比较关注性能和网络带宽，可以使用这种算法。
    // gzip 压缩算法一般会占用较多的 CPU，但会提供更高的压缩比，所以如果网络带宽比较有限，可以使用这种算法。
    // 消息压缩是一种使用时间换空间的优化方式，如果对时延有一定的要求，则不推荐对消息进行压缩
    props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "none");
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        org.apache.kafka.common.serialization.IntegerSerializer.class.getName());
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        org.apache.kafka.common.serialization.StringSerializer.class.getName());
    // KafkaProducer 是线程安全的，可以在多个线程中共享单个 KafkaProducer 实例，也可以将 KafkaProducer 实例进行池化来供其他线程调用。
    KafkaProducer<Integer, String> producer = new KafkaProducer<>(props);

    int messageNo = 1;
    String messageStr = "Message_" + messageNo;
    long startTime = System.currentTimeMillis();
    // TODO 同步发送
    try {
      Future<RecordMetadata> future = producer.send(new ProducerRecord<>(TOPIC, messageNo,
          messageStr));
      // 调用 Future 对象的 get() 方法等待 Kafka 响应。
      RecordMetadata metadata = future.get();
      long elapsedTime = System.currentTimeMillis() - startTime;
//      System.out.println("==== Sent message: (" + messageNo + ", " + messageStr + ")");
      System.out.println(
          "==== Sent message: (" + messageStr
              + ") sent to partition(" + metadata.partition()
              + "), " + "offset(" + metadata.offset()
              + ") in " + elapsedTime + " ms");
    } catch (Exception e) {
      //  KafkaProducer 一般会发生两种类型的异常 可重试的异常和不可重试的异常 。
      //  常见的可重试异常有 NetworkException LeaderNotAvailableException UnknownTopicOrPartitionException NotEnoughReplicasException NotCoordinatorException 等。
      //  比如 NetworkException 表示网络异常，这个有可能是由于网络瞬时故障而导致的异常，可以通过重试解决；
      //  又比如 LeaderNotAvailableException 表示分区的 leader 副本不可用，这个异常通常发生在 leader 副本下线而新的 leader 副本选举完成之前，重试之后可以重新恢复。
      //  不可重试的异常，比如 RecordTooLargeException 异常，暗示了所发送的消息太大， KafkaProducer 对此不会进行任何重试 直接抛出异常
      e.printStackTrace();
    }

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        // close() 方法会阻塞等待之 所有 发送请求完成后再关 KafkaProducer 。
        producer.close();
      }
    });
  }

}
