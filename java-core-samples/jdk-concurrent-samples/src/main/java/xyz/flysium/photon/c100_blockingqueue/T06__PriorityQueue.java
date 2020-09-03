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

package xyz.flysium.photon.c100_blockingqueue;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * PriorityQueue 基于优先级的队列（可扩容），默认情况下采用自然顺序排列，也可以通过比较器 Comparator 指定排序规则
 *
 * @author Sven Augustus
 */
public class T06__PriorityQueue {

  //  PriorityQueue 优先队列不是按照普通对象先进先出原FIFO则进行数据操作，其中的元素有优先级属性，优先级高的元素先出队。
  //  PriorityQueue 使用数组表示的小顶堆实现（ 最小堆是一个完全二叉树，根节点必定是最小节点，子女节点一定大于其父节点。）
  // PriorityQueue队列不适合进场出队入队的频繁操作，但是他的优先级特性非常适合一些对顺序有要求的数据处理场合。

  public static void main(String[] args) {
    Queue<String> queue = new PriorityQueue<>(new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        return (o1 == null) ? (o2 == null ? 0 : 1) : o1.compareTo(o2);
      }
    });

    queue.offer("b");
    queue.offer("a");
    queue.offer("d");
    queue.offer("c");
    queue.offer("e");

    String s = null;
    while ((s = queue.poll()) != null) {
      System.out.println(s);
    }

  }


}
