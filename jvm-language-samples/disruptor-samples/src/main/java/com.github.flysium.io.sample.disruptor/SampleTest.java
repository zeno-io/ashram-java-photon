package com.github.flysium.io.sample.disruptor;

import com.github.flysium.io.sample.disruptor.sample.LongEvent;
import com.github.flysium.io.sample.disruptor.sample.LongEventConsumer;
import com.github.flysium.io.sample.disruptor.sample.LongEventFactory;
import com.github.flysium.io.sample.disruptor.sample.LongEventProducer;
import com.github.flysium.io.sample.disruptor.sample.LongEventWorkHandler;
import com.github.flysium.io.sample.disruptor.sample2.TradeTransaction;
import com.github.flysium.io.sample.disruptor.sample2.TradeTransactionJmsNotifyHandler;
import com.github.flysium.io.sample.disruptor.sample2.TradeTransactionProducer;
import com.github.flysium.io.sample.disruptor.sample2.TradeTransactionStorageConsumer;
import com.github.flysium.io.sample.disruptor.sample2.TradeTransactionVasConsumer;
import com.github.flysium.io.sample.disruptor.trace.CounterTracer;
import com.github.flysium.io.sample.disruptor.trace.CounterTracerFactory;
import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test for Long Event.
 *
 * @author Sven Augustus
 */
public class SampleTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(SampleTest.class);

  @SuppressWarnings("all")
  public static void main(String[] args) throws InterruptedException {
    ThreadFactory threadFactory = new ThreadFactory() {
      private int counter = 0;
      private String prefix = "DisruptorWorker";

      @Override
      public Thread newThread(Runnable r) {
        return new Thread(r, prefix + "-" + counter++);
      }
    };

    EventCallback<LongEvent> loggerLongEventCallback = new EventCallback<LongEvent>() {

      @Override
      public void callback(LongEvent event) {
        LOGGER.info(Thread.currentThread().getName() + " | Event : " + event);
      }
    };
    EventCallback<LongEvent> emptyLongEventCallback = new EventCallback<LongEvent>() {

      @Override
      public void callback(LongEvent event) {
        // do nothing.
      }
    };
    EventCallback<TradeTransaction> tradeTransactionEventCallback = new EventCallback<TradeTransaction>() {

      @Override
      public void callback(TradeTransaction event) {
        // do nothing.
      }
    };

    MessageBuilder integerMessageBuilder = new MessageBuilder() {
      @Override
      public Object build(int index) {
        return index;
      }
    };
    MessageBuilder stringMessageBuilder = new MessageBuilder() {
      @Override
      public Object build(int index) {
        return String.format("body-%s", index);
      }
    };
//  等待策略的选择：
//    BusySpinWaitStrategy ： 自旋等待，类似Linux Kernel使用的自旋锁。低延迟但同时对CPU资源的占用也多。
//    BlockingWaitStrategy ： 使用锁和条件变量。CPU资源的占用少，延迟大。
//    SleepingWaitStrategy ： 在多次循环尝试不成功后，选择让出CPU，等待下次调度，多次调度后仍不成功，尝试前睡眠一个纳秒级别的时间再尝试。这种策略平衡了延迟和CPU资源占用，但延迟不均匀。
//    YieldingWaitStrategy ： 在多次循环尝试不成功后，选择让出CPU，等待下次调。平衡了延迟和CPU资源占用，但延迟也比较均匀。
//    PhasedBackoffWaitStrategy ： 上面多种策略的综合，CPU资源的占用少，延迟大。
    WaitStrategy waitStrategy;
    // waitStrategy = new YieldingWaitStrategy();
    waitStrategy = new SleepingWaitStrategy();

    final int times = 1024 * 1024;

    System.out.println("本系统的CPU核数：" + Runtime.getRuntime().availableProcessors());

    // ArrayBlockingQueue: 只有一个 Producer 和一个 Consumer: 消息是整数
    testArrayBlockingQueue1P1C("1P1C-整数-", times,
        Executors.newSingleThreadExecutor(),
        emptyLongEventCallback,
        integerMessageBuilder);

    // ArrayBlockingQueue: 只有一个 Producer 和一个 Consumer: 消息是字符串
    testArrayBlockingQueue1P1C("1P1C-字符串-", times,
        Executors.newSingleThreadExecutor(),
        emptyLongEventCallback,
        stringMessageBuilder);

    //  Disruptor: 只有一个 Producer 和一个 Consumer: 消息是整数
    testDisruptor1P1C("1P1C-整数-", times, waitStrategy,
        threadFactory,
        emptyLongEventCallback,
        integerMessageBuilder);

    // Disruptor: 只有一个 Producer 和一个 Consumer: 消息是字符串
    testDisruptor1P1C("1P1C-字符串-", times, waitStrategy,
        threadFactory,
        emptyLongEventCallback,
        stringMessageBuilder);

    // Disruptor: 只有一个 Producer 和 8个 Consumer (Multicast): 消息是字符串
    testDisruptor1PnConsumers("1P8C (Multicast)-字符串-", times, waitStrategy,
        threadFactory, 8,
        emptyLongEventCallback,
        stringMessageBuilder, true);

    // Disruptor: 只有一个 Producer 和 8个 Consumer (EventBus): 消息是字符串
    testDisruptor1PnConsumers("1P8C (EventBus)-字符串-", times, waitStrategy,
        threadFactory, 8,
        emptyLongEventCallback,
        stringMessageBuilder, false);

    // Disruptor: 只有一个 Producer 和 12个 Consumer (EventBus): 消息是字符串
    testDisruptor1PnConsumers("1P12C (EventBus)-字符串-", times, waitStrategy,
        threadFactory, 12,
        emptyLongEventCallback,
        stringMessageBuilder, false);

    // Disruptor: 只有一个 Producer 和 32个 Consumer (EventBus): 消息是字符串
    testDisruptor1PnConsumers("1P32C (EventBus)-字符串-", times, waitStrategy,
        threadFactory, 32,
        emptyLongEventCallback,
        stringMessageBuilder, false);

    // Disruptor (sample2): 只有一个 Producer 和 3个 串起来的Consumer（增值业务的消费者C1和负责数据存储的消费者C2、负责发送JMS消息的消费者C3）: 消息是字符串
    testDisruptor1P3CCSample2("sample2-1P3CC (Multicast)-字符串-", times, waitStrategy,
        threadFactory,
        tradeTransactionEventCallback, tradeTransactionEventCallback, tradeTransactionEventCallback,
        stringMessageBuilder);

//        本系统的CPU核数：12
//        ArrayBlockingQueue-1P1C-整数-总耗时：226 ms
//        ArrayBlockingQueue-1P1C-整数-每秒吞吐量：4599018 ops/s
//        ArrayBlockingQueue-1P1C-字符串-总耗时：1471 ms
//        ArrayBlockingQueue-1P1C-字符串-每秒吞吐量：712832 ops/s
//        Disruptor-1P1C-整数-总耗时：62 ms
//        Disruptor-1P1C-整数-每秒吞吐量：16912516 ops/s
//        Disruptor-1P1C-字符串-总耗时：678 ms
//        Disruptor-1P1C-字符串-每秒吞吐量：1546572 ops/s
//        Disruptor-1P8C (Multicast)-字符串-总耗时：1313 ms
//        Disruptor-1P8C (Multicast)-字符串-每秒吞吐量：798611 ops/s
//        Disruptor-1P8C (EventBus)-字符串-总耗时：1141 ms
//        Disruptor-1P8C (EventBus)-字符串-每秒吞吐量：918997 ops/s
//        Disruptor-1P12C (EventBus)-字符串-总耗时：1492 ms
//        Disruptor-1P12C (EventBus)-字符串-每秒吞吐量：702328 ops/s
//        Disruptor-1P32C (EventBus)-字符串-总耗时：3073 ms
//        Disruptor-1P32C (EventBus)-字符串-每秒吞吐量：341111 ops/s

    // 结论一：消息体越大，耗时越久
    // 结论二：Disruptor 的性能比 ArrayBlockingQueue 高出了几乎一个数量级
    // 结论三：Disruptor 尽可能采用单一生产者模式。
  }

  /**
   * ArrayBlockingQueue ：只有一个 Producer 和一个 Consumer
   */
  private static void testArrayBlockingQueue1P1C(String title, int times,
      ExecutorService executor,
      EventCallback<LongEvent> callback,
      MessageBuilder messageBuilder) throws InterruptedException {
    // 测试结果跟踪器
    CounterTracer counterTracer = new CounterTracerFactory().newInstance(true, times);

    ArrayBlockingQueue<LongEvent> queue = new ArrayBlockingQueue<>(times);

    LongEventFactory factory = new LongEventFactory();

    Thread producer = new Thread(() -> {
      for (int i = 0; i < times; i++) {
        LongEvent event = factory.newInstance();
        event.setMsg(messageBuilder.build(i));
        queue.add(event);
      }
    });
    executor.submit(() -> {
      try {
        LongEvent evt;
        while (true) {
          evt = queue.take();
          callback.callback(evt);
          if (counterTracer.count()) {
            //完成后自动结束处理线程；
            break;
          }
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    long beginTime = System.currentTimeMillis();
    producer.start();

    //等待事件处理完成；
    counterTracer.waitForReached();

    executor.shutdown();

    System.out.println(String
        .format("ArrayBlockingQueue-" + title + "总耗时：%d ms",
            (System.currentTimeMillis() - beginTime)));
    System.out.println(String
        .format("ArrayBlockingQueue-" + title + "每秒吞吐量：%.0f ops/s",
            (times * 1000.0 / (System.currentTimeMillis() - beginTime))));
  }

  /**
   * Disruptor ：只有一个 Producer 和一个 Consumer
   */
  private static void testDisruptor1P1C(String title, int times,
      WaitStrategy waitStrategy,
      ThreadFactory threadFactory,
      EventCallback<LongEvent> callback,
      MessageBuilder messageBuilder) throws InterruptedException {
    testDisruptor1PnConsumers(title, times, waitStrategy,
        threadFactory, 1, callback, messageBuilder, true);
  }

  /**
   * Disruptor ：一个 Producer 和 n个 Consumer
   */
  private static void testDisruptor1PnConsumers(String title, int times,
      WaitStrategy waitStrategy,
      ThreadFactory threadFactory, int n,
      EventCallback<LongEvent> callback,
      MessageBuilder messageBuilder, boolean multiCast) throws InterruptedException {
    // 测试结果跟踪器
    CounterTracer counterTracer = new CounterTracerFactory().newInstance(n == 1, times);

    // 事件工厂
    LongEventFactory factory = new LongEventFactory();

    // 环形数组的容量，必须要是2的次幂
    int bufferSize = 1024 * 1024;

    // 构造 Disruptor
    Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize,
        threadFactory, ProducerType.SINGLE, waitStrategy);

    if (multiCast) {
      // 设置消费者, 多个消费者各自处理事件（Multicast）
      for (int i = 0; i < n; ++i) {
        disruptor.handleEventsWith(new LongEventConsumer(event -> {
          callback.callback(event);
          counterTracer.count();
        }));
      }
    } else {
      // 设置消费者, 多个消费者合作处理一批事件（EventBus）
      WorkHandler<LongEvent>[] consumers = new LongEventWorkHandler[n];
      for (int i = 0; i < consumers.length; i++) {
        consumers[i] = new LongEventWorkHandler((EventCallback<LongEvent>) event -> {
          callback.callback(event);
          counterTracer.count();
        });
      }
      disruptor.handleEventsWithWorkerPool(consumers);
    }

    // 设置异常处理器
    disruptor.setDefaultExceptionHandler(new EventExceptionHandler<>());

    // 启动 Disruptor
    disruptor.start();

    // 生产者要使用 Disruptor 的环形数组
    RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

    LongEventProducer producer = new LongEventProducer(ringBuffer);

    // 模拟消息发送
    long beginTime = System.currentTimeMillis();

    for (int i = 0; i < times; i++) {
      producer.onData(messageBuilder.build(i));
    }

    //等待事件处理完成；
    counterTracer.waitForReached();

    disruptor.shutdown();// 关闭 disruptor，方法会堵塞，直至所有的事件都得到处理；

    System.out.println(String
        .format("Disruptor-" + title + "总耗时：%d ms", (System.currentTimeMillis() - beginTime)));
    System.out.println(String
        .format("Disruptor-" + title + "每秒吞吐量：%.0f ops/s",
            (times * 1000.0 / (System.currentTimeMillis() - beginTime))));
  }

  /**
   * Disruptor (sample2) ：一个 Producer 和 3个 串起来的Consumer（增值业务的消费者C1和负责数据存储的消费者C2、负责发送JMS消息的消费者C3）
   */
  private static void testDisruptor1P3CCSample2(String title, int times,
      WaitStrategy waitStrategy,
      ThreadFactory threadFactory,
      EventCallback<TradeTransaction> callback1,
      EventCallback<TradeTransaction> callback2,
      EventCallback<TradeTransaction> callback3,
      MessageBuilder messageBuilder) throws InterruptedException {
    // 测试结果跟踪器
    CounterTracer counterTracer = new CounterTracerFactory().newInstance(true, times);

    // 环形数组的容量，必须要是2的次幂
    int bufferSize = 1024;

    // 事件工厂
    EventFactory<TradeTransaction> factory = TradeTransaction::new;

    // 构造 Disruptor
    Disruptor<TradeTransaction> disruptor = new Disruptor<>(factory, bufferSize, threadFactory,
        ProducerType.SINGLE, new BusySpinWaitStrategy());

    disruptor
        // 使用disruptor创建消费者组C1,C2
        .handleEventsWith(new TradeTransactionVasConsumer(callback1),
            new TradeTransactionStorageConsumer(callback2))
        // 声明在C1,C2完事之后执行JMS消息发送操作 也就是流程走到C3
        .then(new TradeTransactionJmsNotifyHandler(event -> {
          callback3.callback(event);
          counterTracer.count();
        }));

    // 设置异常处理器
    disruptor.setDefaultExceptionHandler(new EventExceptionHandler<>());

    // 启动 Disruptor
    disruptor.start();

    // 生产者要使用 Disruptor 的环形数组
    RingBuffer<TradeTransaction> ringBuffer = disruptor.getRingBuffer();
    TradeTransactionProducer producer = new TradeTransactionProducer(ringBuffer);

    // 模拟消息发送
    long beginTime = System.currentTimeMillis();

    for (int i = 0; i < times; i++) {
      producer.onData(messageBuilder.build(i));
    }

    //等待事件处理完成；
    counterTracer.waitForReached();

    disruptor.shutdown();// 关闭 disruptor，方法会堵塞，直至所有的事件都得到处理；

    System.out.println(String
        .format("Disruptor-" + title + "总耗时：%d ms", (System.currentTimeMillis() - beginTime)));
    System.out.println(String
        .format("Disruptor-" + title + "每秒吞吐量：%.0f ops/s",
            (times * 1000.0 / (System.currentTimeMillis() - beginTime))));
  }

}
