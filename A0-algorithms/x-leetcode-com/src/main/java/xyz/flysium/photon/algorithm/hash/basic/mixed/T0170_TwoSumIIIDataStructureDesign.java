package xyz.flysium.photon.algorithm.hash.basic.mixed;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 170. 两数之和 III - 数据结构设计
 *
 * @author zeno
 */
public interface T0170_TwoSumIIIDataStructureDesign {

  // 设计并实现一个 TwoSum 的类，使该类需要支持 add 和 find 的操作。
  //
  // add 操作 -  对内部数据结构增加一个数。
  // find 操作 - 寻找内部数据结构中是否存在一对整数，使得两数之和与给定的数相等。
  //

  // 	17 ms 100.00%
  class TwoSum {

    private final List<Integer> l;
    private long min = Integer.MAX_VALUE;
    private long max = Integer.MIN_VALUE;
    private long min2 = Integer.MAX_VALUE;
    private long max2 = Integer.MIN_VALUE;

    /**
     * Initialize your data structure here.
     */
    public TwoSum() {
      l = new LinkedList<>();
    }

    /**
     * Add the number to an internal data structure..
     */
    public void add(int number) {
      l.add(number);
      min = Math.min(min, number);
      max = Math.max(max, number);
      min2 = min << 1;
      max2 = max << 1;
    }

    /**
     * Find if there exists any pair of numbers which sum is equal to the value.
     */
    public boolean find(int value) {
      if (value < min2 || value > max2) {
        return false;
      }
      Set<Integer> set = new HashSet<>();
      for (int i : l) {
        if (set.contains(value - i)) {
          return true;
        }
        set.add(i);
      }
      return false;
    }

  }

/**
 * Your TwoSum object will be instantiated and called as such: TwoSum obj = new TwoSum();
 * obj.add(number); boolean param_2 = obj.find(value);
 */

}
