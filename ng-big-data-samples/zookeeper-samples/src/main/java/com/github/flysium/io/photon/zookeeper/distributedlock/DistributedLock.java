/*
 * Apache License 2.0
 *
 * Copyright 2018-2025 the original author or authors.
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

package com.github.flysium.io.photon.zookeeper.distributedlock;

import java.io.Closeable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分布式锁
 *
 * @author Sven Augustus
 * @version 1.0
 */
public interface DistributedLock extends Closeable {

  final Logger logger = LoggerFactory.getLogger(DistributedLock.class);

  /**
   * 阻塞获得锁
   *
   * @throws Exception 获得锁过程异常
   */
  default void lock() throws Exception {
    tryLock(-1);
  }

  /**
   * 尝试阻塞获得锁，除非超时 timeout 毫秒
   *
   * @param timeout 超时时间，单位毫秒
   * @return 是否获得锁
   * @throws Exception 获得锁过程异常
   */
  boolean tryLock(int timeout) throws Exception;

  /**
   * 释放锁
   *
   * @throws Exception 释放锁过程异常
   */
  void unlock() throws Exception;

}
