package xyz.flysium.photon.algorithm.linkedlist.basic;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.Assert;
import org.junit.Test;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0707_DesignLinkedListTest {

  @Test
  public void test() {
    int times = 10000;
    ThreadLocalRandom random = ThreadLocalRandom.current();
    for (int time = 0; time < times; time++) {
      T0707_DesignLinkedList.MyLinkedList linkedList = new T0707_DesignLinkedList.MyLinkedList();
      LinkedList<Integer> jdkList = new LinkedList<>();
      for (int i = 0; i < 200; i++) {
        if (jdkList.isEmpty()) {
          int e = random.nextInt(100);
          jdkList.addLast(e);
          linkedList.addAtTail(e);
          Assert.assertEquals(jdkList.get(0).intValue(), linkedList.get(0));
        } else {
          int possibility = random.nextInt(100);
          if (possibility < 20) {
            int e = random.nextInt(100);
            jdkList.addFirst(e);
            linkedList.addAtHead(e);
            Assert.assertEquals(jdkList.get(0).intValue(), linkedList.get(0));
          } else if (possibility < 40) {
            int e = random.nextInt(100);
            int index = random.nextInt(jdkList.size());
            jdkList.add(index, e);
            linkedList.addAtIndex(index, e);
            Assert.assertEquals(jdkList.get(0).intValue(), linkedList.get(0));
          } else if (possibility < 65) {
            int index = random.nextInt(jdkList.size());
            jdkList.remove(index);
            linkedList.deleteAtIndex(index);
          } else {
            int index = random.nextInt(jdkList.size());
            Integer excepted = jdkList.get(index);
            int actual = linkedList.get(index);
            Assert.assertEquals(excepted.intValue(), actual);
          }
        }
      }
    }
  }

  @Test
  public void test1() {
    int times = 10000;
    ThreadLocalRandom random = ThreadLocalRandom.current();
    for (int time = 0; time < times; time++) {
      T0707_DesignLinkedList_1.MyLinkedList linkedList = new T0707_DesignLinkedList_1.MyLinkedList();
      LinkedList<Integer> jdkList = new LinkedList<>();
      for (int i = 0; i < 200; i++) {
        if (jdkList.isEmpty()) {
          int e = random.nextInt(100);
          jdkList.addLast(e);
          linkedList.addAtTail(e);
          Assert.assertEquals(jdkList.get(0).intValue(), linkedList.get(0));
        } else {
          int possibility = random.nextInt(100);
          if (possibility < 20) {
            int e = random.nextInt(100);
            jdkList.addFirst(e);
            linkedList.addAtHead(e);
            Assert.assertEquals(jdkList.get(0).intValue(), linkedList.get(0));
          } else if (possibility < 40) {
            int e = random.nextInt(100);
            int index = random.nextInt(jdkList.size());
            jdkList.add(index, e);
            linkedList.addAtIndex(index, e);
            Assert.assertEquals(jdkList.get(0).intValue(), linkedList.get(0));
          } else if (possibility < 65) {
            int index = random.nextInt(jdkList.size());
            jdkList.remove(index);
            linkedList.deleteAtIndex(index);
          } else {
            int index = random.nextInt(jdkList.size());
            Integer excepted = jdkList.get(index);
            int actual = linkedList.get(index);
            Assert.assertEquals(excepted.intValue(), actual);
          }
        }
      }
    }
  }

}
