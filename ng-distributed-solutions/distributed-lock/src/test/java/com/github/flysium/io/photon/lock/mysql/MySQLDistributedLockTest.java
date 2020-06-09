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
import com.github.flysium.io.photon.lock.DistributedLockTest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class MySQLDistributedLockTest extends DistributedLockTest {

  public static final String CONNECT_STRING = "jdbc:mysql://127.0.0.1:3306/demo?useSSL=false";
  public static final String USER = "root";
  public static final String PASSWORD = "root123";

  /**
   * Note: You should create table in your mysql database first
   * <p>
   * see {@link MySQLNoFairDistributedLock}
   */
  private AbstractDistributedLock nonReentrantLock;

  @Before
  public void setUp() throws SQLException {
    Connection connection = DriverManager.getConnection(CONNECT_STRING, USER, PASSWORD);
    nonReentrantLock = new MySQLNoFairDistributedLock("myNonReentrantLock", connection, true);
  }

  @After
  public void over() throws IOException {
    if (nonReentrantLock != null) {
      nonReentrantLock.close();
    }
  }

  @Test
  public void testNonReentrantLock() throws InterruptedException {
    testIncrement(nonReentrantLock, 1000);
  }

  @Test
  public void test() throws InterruptedException {

  }

}

