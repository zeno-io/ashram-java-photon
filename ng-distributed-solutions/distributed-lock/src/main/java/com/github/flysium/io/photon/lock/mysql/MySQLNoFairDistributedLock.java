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

package com.github.flysium.io.photon.lock.mysql;

import com.github.flysium.io.photon.lock.AbstractDistributedLock;
import com.github.flysium.io.photon.lock.LockInterruptedException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * a NON re-entrant Lock of MySQL(<a>https://www.mysql.com/</a>) Lock
 *
 * <pre>{@code}
 * create table t_distributed_lock
 * (
 *  id           bigint primary key auto_increment,
 *  lock_key     varchar(50) not null,
 *  unique (lock_key)
 * ) engine = myisam;
 * </pre>
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class MySQLNoFairDistributedLock extends AbstractDistributedLock {

  private final String lockName;
  private final Connection connection;
  private final boolean closeable;

  public MySQLNoFairDistributedLock(String lockName, Connection connection) {
    this(lockName, connection, true);
  }

  public MySQLNoFairDistributedLock(String lockName, Connection connection, boolean closeable) {
    this.lockName = lockName;
    this.connection = connection;
    this.closeable = closeable;
  }

  @Override
  public void lockInterruptibly() throws InterruptedException {
    lockInterruptibly(-1, TimeUnit.MILLISECONDS);
  }

  @Override
  public boolean tryLock() {
    try {
      return lockInterruptibly(-1, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    return false;
  }

  @Override
  public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
    return lockInterruptibly(time, unit);
  }

  private static final String LOCK_SQL = "insert into t_distributed_lock (lock_key) values (?)";

  protected boolean lockInterruptibly(long time, TimeUnit unit) throws InterruptedException {
    long expireTime = (unit == null) ? -1 : unit.convert(time, TimeUnit.MILLISECONDS);
    long startMillis = Instant.now().toEpochMilli();
    boolean lock = false;
    CountDownLatch latch = new CountDownLatch(1);
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(LOCK_SQL);
      preparedStatement.setString(1, lockName);
      for (; ; ) {
        try {
          int i = preparedStatement.executeUpdate();
          if (i > 0) {
            lock = true;
            break;
          }
          latch.await(10, TimeUnit.MILLISECONDS);
          if (expireTime > 0 && Instant.now().toEpochMilli() - startMillis >= expireTime) {
            break;
          }
        } catch (SQLIntegrityConstraintViolationException e) {
          logger.debug("skip key error: " + e.getMessage());
        }
      }
    } catch (SQLException e) {
      throw new LockInterruptedException("lock error: " + e.getMessage(), e);
    }
    return lock;
  }

  private static final String UNLOCK_SQL = "delete from t_distributed_lock where lock_key = ?";

  @Override
  public void unlock() {
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(UNLOCK_SQL);
      preparedStatement.setString(1, lockName);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new LockInterruptedException("unlock error: " + e.getMessage(), e);
    }
  }

  @Override
  public void close() throws IOException {
    if (!closeable) {
      return;
    }
    try {
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException e) {
      throw new IOException(e);
    }
  }

  @Override
  public boolean isReentrantLock() {
    return false;
  }
}
