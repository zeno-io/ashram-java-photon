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
import io.etcd.jetcd.lease.LeaseRevokeResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

  private volatile Long leaseId = null;

  public EtcdDistributedLock(String lockName, Client client) {
    this(lockName, client, 3000);
  }

  public EtcdDistributedLock(String lockName, Client client, long connectionTimeout) {
    this.lockName = lockName;
    this.client = client;
    this.connectionTimeout = connectionTimeout;
  }

  @Override
  public void lockInterruptibly() throws InterruptedException {
    lockInterruptibly(-1, TimeUnit.MILLISECONDS);
  }

  @Override
  public boolean tryLock() {
    return lockInterruptibly(-1, TimeUnit.MILLISECONDS);
  }

  @Override
  public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
    return lockInterruptibly(time, unit);
  }

  protected boolean lockInterruptibly(long time, TimeUnit unit) {
    boolean lock = false;
    try {
      if (leaseId == null) {
        synchronized (this) {
          if (leaseId == null) {
            leaseId = futureGet(client.getLeaseClient()
                    .grant(time < 0 ? -1 : unit.convert(time, TimeUnit.SECONDS)),
                connectionTimeout, TimeUnit.MILLISECONDS)
                .getID();
          }
        }
      }
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      throw new LockInterruptedException("create lease error: " + e.getMessage(), e);
    }
    try {
      futureGet(client.getLockClient()
              .lock(ByteSequence.from(lockName, StandardCharsets.UTF_8), leaseId),
          time, unit);
      lock = true;
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      logger.error("lock resource [" + lockName + "]" + "failed." + e);
      logger.error("revoke lease [" + leaseId + "]");
      revoke();
      throw new LockInterruptedException("lock error: " + e.getMessage(), e);
    }
    return lock;
  }

  @Override
  public void unlock() {
    try {
      futureGet(client.getLockClient()
              .unlock(ByteSequence.from(lockName, StandardCharsets.UTF_8)), connectionTimeout,
          TimeUnit.MILLISECONDS);
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      throw new LockInterruptedException("unlock error: " + e.getMessage(), e);
    }
  }

  protected CompletableFuture<LeaseRevokeResponse> revoke() {
    return client.getLeaseClient().revoke(leaseId);
  }

  @Override
  public void close() throws IOException {
    client.close();
  }

  @Override
  public boolean isReentrantLock() {
    return true;
  }
}
