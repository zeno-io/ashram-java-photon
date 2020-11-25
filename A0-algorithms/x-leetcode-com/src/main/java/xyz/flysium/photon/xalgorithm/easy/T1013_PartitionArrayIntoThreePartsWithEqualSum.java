package xyz.flysium.photon.xalgorithm.easy;

/**
 * 1013. 将数组分成和相等的三个部分
 * <p>
 * https://leetcode-cn.com/problems/partition-array-into-three-parts-with-equal-sum/
 *
 * @author zeno
 */
public class T1013_PartitionArrayIntoThreePartsWithEqualSum {

//给你一个整数数组 A，只有可以将其划分为三个和相等的非空部分时才返回 true，否则返回 false。
//
// 形式上，如果可以找出索引 i+1 < j 且满足 A[0] + A[1] + ... + A[i] == A[i+1] + A[i+2] + ... +
//A[j-1] == A[j] + A[j-1] + ... + A[A.length - 1] 就可以将数组三等分。
//
//
//
// 示例 1：
//
// 输入：[0,2,1,-6,6,-7,9,1,2,0,1]
//输出：true
//解释：0 + 2 + 1 = -6 + 6 - 7 + 9 + 1 = 2 + 0 + 1
//
//
// 示例 2：
//
// 输入：[0,2,1,-6,6,7,9,-1,2,0,1]
//输出：false
//
//
// 示例 3：
//
// 输入：[3,3,6,5,-2,2,5,1,-9,4]
//输出：true
//解释：3 + 3 = 6 = 5 - 2 + 2 + 5 + 1 - 9 + 4
//
//
//
//
// 提示：
//
//
// 3 <= A.length <= 50000
// -10^4 <= A[i] <= 10^4
//
// Related Topics 数组
// 👍 125 👎 0


  public static void main(String[] args) {
    Solution solution = new T1013_PartitionArrayIntoThreePartsWithEqualSum().new Solution();
    System.out.println(solution.
      canThreePartsEqualSum(new int[]{0, 2, 1, -6, 6, -7, 9, 1, 2, 0, 1}));
    System.out.println(solution.
      canThreePartsEqualSum(new int[]{1, -1, 1, -1}));
    System.out.println(solution.
      canThreePartsEqualSum(new int[]{10, -10, 10, -10, 10, -10, 10, -10}));
  }

  // 执行耗时:1 ms,击败了100.00% 的Java用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public boolean canThreePartsEqualSum(int[] A) {
      int total = 0;
      for (int a : A) {
        total += a;
      }
      if (total % 3 != 0) {
        return false;
      }
      int target = total / 3;
      int sum = 0;
      int times = 0;
      for (int i = 0; i < A.length; i++) {
        sum += A[i];
        if (sum == target) {
          times++;
          sum = 0;
        }
        if (times == 2) {
          return i < A.length - 1;
        }
      }

      return times == 3;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
