/*
 * Copyright 2020 SvenAugustus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.lock.etcd;

import com.github.flysium.io.photon.lock.AbstractDistributedLock;
import com.github.flysium.io.photon.lock.LockInterruptedException;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.lease.LeaseKeepAliveResponse;
import io.etcd.jetcd.lock.LockResponse;
import io.grpc.stub.StreamObserver;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Etcd(<a>https://etcd.io/</a>) Distributed Lock
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class EtcdDistributedLock extends AbstractDistributedLock {

  private final String lockName;
  private final Client client;
  private final long connectionTimeout;
  public static final int DEFAULT_TTL = 30000;
  /**
   * key: ttl
   * <p>
   * value: leaseId for hold the lock exists.
   */
  private final Map<Long, Long> ttl2LeaseId = new ConcurrentHashMap<>();
  /**
   * key: leaseId
   * <p>
   * value: the threads which hold the leaseId (lock)
   */
  private final Map<Long, ConcurrentSkipListSet<Thread>> leaseIdThread = new ConcurrentHashMap<>();
  /**
   * re-entrant mutex lock
   */
  private final Map<Thread, LockData> lockDataMap = new ConcurrentHashMap<>();

  public EtcdDistributedLock(String lockName, Client client) {
    this(lockName, client, 3000);
  }

  public EtcdDistributedLock(String lockName, Client client, long connectionTimeout) {
    this.lockName = lockName;
    this.client = client;
    this.connectionTimeout = connectionTimeout;
  }

  @Override
  public void lockInterruptibly() {
    lockInterruptibly(DEFAULT_TTL, TimeUnit.MILLISECONDS);
  }

  @Override
  public boolean tryLock() {
    return lockInterruptibly(DEFAULT_TTL, TimeUnit.MILLISECONDS);
  }

  @Override
  public boolean tryLock(long time, TimeUnit unit) {
    return lockInterruptibly(time, unit);
  }

  protected boolean lockInterruptibly(long time, TimeUnit unit) {
    long s = Instant.now().toEpochMilli();
    LockData lockData = lockDataMap.get(Thread.currentThread());
    if (lockData != null) {
      lockData.lockCount.incrementAndGet();
      return true;
    }
    Long leaseId;
    try {
      leaseId = getLeaseId(unit.convert(time, TimeUnit.SECONDS));
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      throw new LockInterruptedException("create lease error: " + e.getMessage(), e);
    }
    // return if lock success
    try {
      LockResponse lockResponse = futureGet(client.getLockClient()
              .lock(ByteSequence.from(lockName, StandardCharsets.UTF_8), leaseId),
          time, unit);
      if (lockResponse != null) {
        String lockPath = lockResponse.getKey().toString(StandardCharsets.UTF_8);
        logger.info("lock success, the lock path: {}, thread: {}",
            lockPath, Thread.currentThread());
      }
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      logger.error("lock resource [" + lockName + "]" + "failed." + e);
      tryRevokeLease(leaseId);
      return false;
    }
    lockData = new LockData(lockName, leaseId);
    lockDataMap.putIfAbsent(Thread.currentThread(), lockData);
    return true;
  }

  @Override
  public void unlock() {
    LockData lockData = lockDataMap.get(Thread.currentThread());
    if (lockData == null) {
      throw new LockInterruptedException("you do not own the lock: " + lockName);
    }
    int newLockCount = lockData.lockCount.decrementAndGet();
    if (newLockCount > 0) {
      return;
    } else if (newLockCount < 0) {
      throw new LockInterruptedException("lock count has gone negative for lock: " + lockName);
    }
    try {
      futureGet(client.getLockClient()
              .unlock(ByteSequence.from(lockName, StandardCharsets.UTF_8)), connectionTimeout,
          TimeUnit.MILLISECONDS);
      tryRevokeLease(lockData.leaseId);
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      throw new LockInterruptedException("unlock error: " + e.getMessage(), e);
    } finally {
      lockDataMap.remove(Thread.currentThread());
    }
  }

  @Override
  public void close() {
    leaseIdThread.keySet().forEach(this::revokeLease);
    client.getLeaseClient().close();
    client.getLockClient().close();
    client.close();
  }

  @Override
  public boolean isReentrantLock() {
    return true;
  }

  protected Long getLeaseId(long ttl)
      throws InterruptedException, ExecutionException, TimeoutException {
    Long leaseId = ttl2LeaseId.get(ttl);
    if (leaseId == null) {
      synchronized (this) {
        leaseId = ttl2LeaseId.get(ttl);
        if (leaseId == null) {
          leaseId = futureGet(client.getLeaseClient()
                  .grant(ttl),
              connectionTimeout, TimeUnit.MILLISECONDS)
              .getID();
          client.getLeaseClient()
              .keepAlive(leaseId, new StreamObserver<LeaseKeepAliveResponse>() {
                @Override
                public void onNext(LeaseKeepAliveResponse leaseKeepAliveResponse) {
                  logger.info("Keep alive success, ID: {}, ttl: {} ",
                      leaseKeepAliveResponse.getID(),
                      leaseKeepAliveResponse.getTTL());
                }

                @Override
                public void onError(Throwable throwable) {
                  logger.warn("Keep alive error ", throwable);
                }

                @Override
                public void onCompleted() {
                }
              });
          ttl2LeaseId.put(ttl, leaseId);
          leaseIdThread.putIfAbsent(leaseId, new ConcurrentSkipListSet<>(
              (o1, o2) -> o1.getName().compareTo(o2.getName())));
        }
      }
    }
    leaseIdThread.get(leaseId).add(Thread.currentThread());
    return leaseId;
  }

  protected void tryRevokeLease(Long leaseId) {
    ConcurrentSkipListSet<Thread> threads = leaseIdThread.get(leaseId);
    if (threads != null) {
      threads.remove(Thread.currentThread());
      if (threads.isEmpty()) {
        revokeLease(leaseId);
      }
    }
  }

  private void revokeLease(Long leaseId) {
    logger.info("revoke leaseId [{}]" + ".", leaseId);
    client.getLeaseClient().revoke(leaseId);
    ttl2LeaseId.forEach((ttl, l) -> {
      if (l != null && l.equals(leaseId)) {
        ttl2LeaseId.remove(ttl);
      }
    });
  }

  private static class LockData {

    final String lockPath;
    final Long leaseId;
    final AtomicInteger lockCount;

    private LockData(String lockPath, Long leaseId) {
      this.lockPath = lockPath;
      this.leaseId = leaseId;
      this.lockCount = new AtomicInteger(1);
    }
  }

}
