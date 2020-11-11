package xyz.flysium.photon.jianzhioffer.divide.medium;

import xyz.flysium.photon.ArraySupport;

/**
 * 剑指 Offer 33. 二叉搜索树的后序遍历序列
 * <p>
 * https://leetcode-cn.com/problems/er-cha-sou-suo-shu-de-hou-xu-bian-li-xu-lie-lcof/
 *
 * @author zeno
 */
public class J0033_VerifyPostorder {

//输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历结果。如果是则返回 true，否则返回 false。假设输入的数组的任意两个数字都互不相同。
//
//
//
// 参考以下这颗二叉搜索树：
//
//      5
//    / \
//   2   6
//  / \
// 1   3
//
// 示例 1：
//
// 输入: [1,6,3,2,5]
//输出: false
//
// 示例 2：
//
// 输入: [1,3,2,6,5]
//输出: true
//
//
//
// 提示：
//
//
// 数组长度 <= 1000
//
// 👍 133 👎 0


  public static void main(String[] args) {
    Solution solution = new J0033_VerifyPostorder().new Solution();
    // true
    System.out
      .println(solution.verifyPostorder(ArraySupport.newArray("[4, 8, 6, 12, 16, 14, 10]")));
  }

  // 执行用时：0 ms, 在所有 Java 提交中击败了100.00% 的用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public boolean verifyPostorder(int[] postorder) {
      return verifyPostorder(postorder, 0, postorder.length - 1);
    }

    private boolean verifyPostorder(int[] postorder, int l, int r) {
      if (l >= r) {
        return true;
      }
      int head = postorder[r];
      int i = r;
      while (i - 1 >= l && postorder[i - 1] > head) {
        i--;
      }
      int m = i;
      while (i - 1 >= l && postorder[i - 1] < head) {
        i--;
      }
      if (i != l) {
        return false;
      }
      boolean rb = verifyPostorder(postorder, m, r - 1);
      boolean lb = verifyPostorder(postorder, l, m - 1);
      return rb && lb;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
