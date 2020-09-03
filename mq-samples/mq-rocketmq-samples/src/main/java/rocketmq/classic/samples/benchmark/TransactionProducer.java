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

package rocketmq.classic.samples.benchmark;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionExecuter;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class TransactionProducer {
    private static int threadCount;
    private static int messageSize;
    private static boolean ischeck;
    private static boolean ischeckffalse;

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException {
        threadCount = args.length >= 1 ? Integer.parseInt(args[0]) : 32;
        messageSize = args.length >= 2 ? Integer.parseInt(args[1]) : 1024 * 2;
        ischeck = args.length >= 3 && Boolean.parseBoolean(args[2]);
        ischeckffalse = args.length >= 4 && Boolean.parseBoolean(args[3]);

        final Message msg = buildMessage(messageSize);

        final ExecutorService sendThreadPool = Executors.newFixedThreadPool(threadCount);

        final StatsBenchmarkTProducer statsBenchmark = new StatsBenchmarkTProducer();

        final Timer timer = new Timer("BenchmarkTimerThread", true);

        final LinkedList<Long[]> snapshotList = new LinkedList<Long[]>();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                snapshotList.addLast(statsBenchmark.createSnapshot());
                while (snapshotList.size() > 10) {
                    snapshotList.removeFirst();
                }
            }
        }, 1000, 1000);

        timer.scheduleAtFixedRate(new TimerTask() {
            private void printStats() {
                if (snapshotList.size() >= 10) {
                    Long[] begin = snapshotList.getFirst();
                    Long[] end = snapshotList.getLast();

                    final long sendTps =
                        (long) (((end[3] - begin[3]) / (double) (end[0] - begin[0])) * 1000L);
                    final double averageRT = (end[5] - begin[5]) / (double) (end[3] - begin[3]);

                    System.out.printf(
                        "Send TPS: %d Max RT: %d Average RT: %7.3f Send Failed: %d Response Failed: %d transaction checkCount: %d %n",
                        sendTps, statsBenchmark.getSendMessageMaxRT().get(), averageRT, end[2], end[4], end[6]);
                }
            }

            @Override
            public void run() {
                try {
                    this.printStats();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 10000, 10000);

        final TransactionCheckListener transactionCheckListener =
            new TransactionCheckListenerBImpl(ischeckffalse, statsBenchmark);
        final TransactionMQProducer producer = new TransactionMQProducer("benchmark_transaction_producer");
        producer.setInstanceName(Long.toString(System.currentTimeMillis()));
        producer.setTransactionCheckListener(transactionCheckListener);
        producer.setDefaultTopicQueueNums(1000);
        producer.start();

        final TransactionExecuterBImpl tranExecuter = new TransactionExecuterBImpl(ischeck);

        for (int i = 0; i < threadCount; i++) {
            sendThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            // Thread.sleep(1000);
                            final long beginTimestamp = System.currentTimeMillis();
                            SendResult sendResult =
                                producer.sendMessageInTransaction(msg, tranExecuter, null);
                            if (sendResult != null) {
                                statsBenchmark.getSendRequestSuccessCount().incrementAndGet();
                                statsBenchmark.getReceiveResponseSuccessCount().incrementAndGet();
                            }

                            final long currentRT = System.currentTimeMillis() - beginTimestamp;
                            statsBenchmark.getSendMessageSuccessTimeTotal().addAndGet(currentRT);
                            long prevMaxRT = statsBenchmark.getSendMessageMaxRT().get();
                            while (currentRT > prevMaxRT) {
                                boolean updated =
                                    statsBenchmark.getSendMessageMaxRT().compareAndSet(prevMaxRT,
                                        currentRT);
                                if (updated)
                                    break;

                                prevMaxRT = statsBenchmark.getSendMessageMaxRT().get();
                            }
                        } catch (MQClientException e) {
                            statsBenchmark.getSendRequestFailedCount().incrementAndGet();
                        }
                    }
                }
            });
        }
    }

    private static Message buildMessage(final int messageSize) throws UnsupportedEncodingException {
        Message msg = new Message();
        msg.setTopic("BenchmarkTest");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < messageSize; i += 10) {
            sb.append("hello baby");
        }

        msg.setBody(sb.toString().getBytes(RemotingHelper.DEFAULT_CHARSET));

        return msg;
    }
}

class TransactionExecuterBImpl implements LocalTransactionExecuter {

    private boolean ischeck;

    public TransactionExecuterBImpl(boolean ischeck) {
        this.ischeck = ischeck;
    }

    @Override
    public LocalTransactionState executeLocalTransactionBranch(final Message msg, final Object arg) {
        if (ischeck) {
            return LocalTransactionState.UNKNOW;
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}

class TransactionCheckListenerBImpl implements TransactionCheckListener {
    private boolean ischeckffalse;
    private StatsBenchmarkTProducer statsBenchmarkTProducer;

    public TransactionCheckListenerBImpl(boolean ischeckffalse,
        StatsBenchmarkTProducer statsBenchmarkTProducer) {
        this.ischeckffalse = ischeckffalse;
        this.statsBenchmarkTProducer = statsBenchmarkTProducer;
    }

    @Override
    public LocalTransactionState checkLocalTransactionState(MessageExt msg) {
        statsBenchmarkTProducer.getCheckRequestSuccessCount().incrementAndGet();
        if (ischeckffalse) {

            return LocalTransactionState.ROLLBACK_MESSAGE;
        }

        return LocalTransactionState.COMMIT_MESSAGE;
    }
}

class StatsBenchmarkTProducer {
    private final AtomicLong sendRequestSuccessCount = new AtomicLong(0L);

    private final AtomicLong sendRequestFailedCount = new AtomicLong(0L);

    private final AtomicLong receiveResponseSuccessCount = new AtomicLong(0L);

    private final AtomicLong receiveResponseFailedCount = new AtomicLong(0L);

    private final AtomicLong sendMessageSuccessTimeTotal = new AtomicLong(0L);

    private final AtomicLong sendMessageMaxRT = new AtomicLong(0L);

    private final AtomicLong checkRequestSuccessCount = new AtomicLong(0L);

    public Long[] createSnapshot() {
        Long[] snap = new Long[] {
            System.currentTimeMillis(),
            this.sendRequestSuccessCount.get(),
            this.sendRequestFailedCount.get(),
            this.receiveResponseSuccessCount.get(),
            this.receiveResponseFailedCount.get(),
            this.sendMessageSuccessTimeTotal.get(),
            this.checkRequestSuccessCount.get()};

        return snap;
    }

    public AtomicLong getSendRequestSuccessCount() {
        return sendRequestSuccessCount;
    }

    public AtomicLong getSendRequestFailedCount() {
        return sendRequestFailedCount;
    }

    public AtomicLong getReceiveResponseSuccessCount() {
        return receiveResponseSuccessCount;
    }

    public AtomicLong getReceiveResponseFailedCount() {
        return receiveResponseFailedCount;
    }

    public AtomicLong getSendMessageSuccessTimeTotal() {
        return sendMessageSuccessTimeTotal;
    }

    public AtomicLong getSendMessageMaxRT() {
        return sendMessageMaxRT;
    }

    public AtomicLong getCheckRequestSuccessCount() {
        return checkRequestSuccessCount;
    }
}
