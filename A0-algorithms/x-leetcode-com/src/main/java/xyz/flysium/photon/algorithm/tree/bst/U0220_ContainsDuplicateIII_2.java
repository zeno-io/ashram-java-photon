package xyz.flysium.photon.algorithm.tree.bst;

import java.util.HashMap;
import java.util.Map;

/**
 * 220. 存在重复元素 III
 * <p>
 * https://leetcode-cn.com/problems/contains-duplicate-iii/
 *
 * @author zeno
 */
public interface U0220_ContainsDuplicateIII_2 {

  // 在整数数组 nums 中，是否存在两个下标 i 和 j，
  // 使得 nums [i] 和 nums [j] 的差的绝对值小于等于 t ，且满足 i 和 j 的差的绝对值也小于等于 ķ 。
  //
  // 如果存在则返回 true，不存在返回 false。
  //

  // 22 ms
  class Solution {

    // Bucket Sort
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
      if (nums.length == 0 || k <= 0 || t < 0) {
        return false;
      }
      final long tLong = t;
      final long bucketWidth = tLong + 1;
      // bucketID -1: contains number [-t-1,-1]
      // bucketID  0: contains number [0,t]
      // bucketID  1: contains number [t+1,2t+1]
      // bucketID  1: contains number [2t+2,3t+1]
      // ...
      Map<Long, Long> bucket = new HashMap<>(nums.length);
      for (
        int i = 0;
        i < nums.length; i++) {
        long number = (long) nums[i];
        long bucketId = getBucketId(number, bucketWidth);
        // check if bucket m is empty, each bucket may contain at most one element
        if (bucket.containsKey(bucketId)) {
          return true;
        }
        // check the nei***or buckets for almost duplicate
        if (bucket.containsKey(bucketId - 1)
          && number <= bucket.get(bucketId - 1) + tLong) {
          return true;
        }
        if (bucket.containsKey(bucketId + 1)
          && number >= bucket.get(bucketId + 1) - tLong) {
          return true;
        }
        // now bucket m is empty and no almost duplicate in nei***or buckets
        bucket.put(bucketId, number);
        // remove the oldest
        if (i >= k) {
          bucket.remove(getBucketId(nums[i - k], bucketWidth));
        }
      }
      return false;
    }

    // Get the ID of the bucket from element value x and bucket width w
    // In Java, `-3 / 5 = 0` and but we need `-3 / 5 = -1`.
    private long getBucketId(long num, long bucketWidth) {
      if (num >= 0) {
        return num / bucketWidth;
      }
      return ((num + 1) / bucketWidth) - 1;
    }

  }

}
