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
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import kafka.classic.samples.KafkaTest;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

/**
 * 生产者: 自定义分区器 Partitioner
 *
 * @author Sven Augustus
 */
public class ProducerWithPartitioner extends KafkaTest {

  public static final String TOPIC = "DemoTopic1";

  public static void main(String[] args) {
    setKafkaLogger(Level.INFO);

    Properties props = new Properties();
    // Kafka 服务器地址
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092,127.0.0.1:9093");
    props.put(ProducerConfig.CLIENT_ID_CONFIG, "DemoProducerClient4");
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        org.apache.kafka.common.serialization.IntegerSerializer.class.getName());
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        org.apache.kafka.common.serialization.StringSerializer.class.getName());
    // TODO 可以指定分区器，默认是 org.apache.kafka.clients.producer.internals.DefaultPartitioner。在默认分区器 DefaultPartitioner 的实现中， 在 partition()方法中定义了主要的分区分配逻辑。
    //  如果 key 不为 null ，那么默认的分区器会对 key 进行哈希（采MurmurHash2 算法，具备高运算性能及低碰撞率），最终根据得到 哈希值来计算分区号，有相同 key 的消息会被写入同一个分区。
    //  如果 key null ，那么消息将会以轮询的方式发往主题内的各个可用分区。
    props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, DemoPartitioner.class.getName());
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

  public static class DemoPartitioner implements Partitioner {

    private final ConcurrentMap<String, AtomicInteger> topicCounterMap = new ConcurrentHashMap<>();

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes,
        Cluster cluster) {
      List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
      int numPartitions = partitions.size();
      if (keyBytes == null) {
        int nextValue = nextValue(topic);
        List<PartitionInfo> availablePartitions = cluster.availablePartitionsForTopic(topic);
//        if (availablePartitions.size() > 0) {
//          int part = Utils.toPositive(nextValue) % availablePartitions.size();
//          return availablePartitions.get(part).partition();
//        } else {
        // no partitions are available, give a non-available partition
        return Utils.toPositive(nextValue) % numPartitions;
//        }
      } else {
        // hash the keyBytes to choose a partition
        return Utils.toPositive(Utils.murmur2(keyBytes)) % numPartitions;
      }
    }

    private int nextValue(String topic) {
      AtomicInteger counter = topicCounterMap.get(topic);
      if (null == counter) {
        counter = new AtomicInteger(ThreadLocalRandom.current().nextInt());
        AtomicInteger currentCounter = topicCounterMap.putIfAbsent(topic, counter);
        if (currentCounter != null) {
          counter = currentCounter;
        }
      }
      return counter.getAndIncrement();
    }

    @Override
    public void close() {
    }

    @Override
    public void configure(Map<String, ?> configs) {
    }
  }

}
