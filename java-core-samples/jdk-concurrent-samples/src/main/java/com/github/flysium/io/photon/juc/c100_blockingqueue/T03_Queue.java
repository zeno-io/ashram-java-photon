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

package com.github.flysium.io.photon.juc.c100_blockingqueue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Queue
 *
 * @author Sven Augustus
 */
public class T03_Queue {

  public static void main(String[] args) {
    Queue queue = new ConcurrentLinkedQueue();
    for (int i = 0; i < 10; i++) {
//      queue.add("a" + i);
      queue.offer("a" + i);
    }

//    queue.remove();
//    queue.element();

    queue.offer("2");
    System.out.println(queue.poll());
    System.out.println(queue.size());
    System.out.println(queue.peek());
    System.out.println(queue.size());

  }

}
