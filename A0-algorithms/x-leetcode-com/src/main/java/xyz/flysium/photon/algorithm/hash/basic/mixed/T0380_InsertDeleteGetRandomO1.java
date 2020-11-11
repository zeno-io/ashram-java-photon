package xyz.flysium.photon.algorithm.hash.basic.mixed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 380. 常数时间插入、删除和获取随机元素
 * <p>
 * https://leetcode-cn.com/problems/insert-delete-getrandom-o1/
 *
 * @author zeno
 */
public interface T0380_InsertDeleteGetRandomO1 {

  // 设计一个支持在平均 时间复杂度 O(1) 下，执行以下操作的数据结构。
  //
  //    insert(val)：当元素 val 不存在时，向集合中插入该项。
  //    remove(val)：元素 val 存在时，从集合中移除该项。
  //    getRandom：随机返回现有集合中的一项。每个元素应该有相同的概率被返回。

  // 11ms 99.10%
  class RandomizedSet {

    // val
    List<Integer> l;
    // val -> index
    Map<Integer, Integer> hash;
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    /**
     * Initialize your data structure here.
     */
    public RandomizedSet() {
      hash = new HashMap<>();
      l = new ArrayList<>();
    }

    /**
     * Inserts a value to the set. Returns true if the set did not already contain the specified
     * element.
     */
    public boolean insert(int val) {
      Integer idx = hash.get(val);
      if (idx == null) {
        idx = l.size();
        l.add(val);
        hash.put(val, idx);
        return true;
      }
      return false;
    }

    /**
     * Removes a value from the set. Returns true if the set contained the specified element.
     */
    public boolean remove(int val) {
      if (l.isEmpty()) {
        return false;
      }
      Integer idx = hash.remove(val);
      if (idx != null) {
        int last = l.size() - 1;
        // not same element
        if (last != idx) {
          Integer le = l.get(last);
          l.set(idx, le);
          // update val -> index
          hash.put(le, idx);
        }
        l.remove(last);
        return true;
      }
      return false;
    }


    /**
     * Get a random element from the set.
     */
    public int getRandom() {
      if (l.size() == 1) {
        return l.get(0);
      }
      return l.get(random.nextInt(0, l.size()));
    }
  }

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */

}
