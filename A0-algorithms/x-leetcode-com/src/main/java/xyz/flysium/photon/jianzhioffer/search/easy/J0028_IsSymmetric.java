package xyz.flysium.photon.jianzhioffer.search.easy;

import xyz.flysium.photon.tree.TreeNode;

/**
 * 剑指 Offer 28. 对称的二叉树
 * <p>
 * https://leetcode-cn.com/problems/dui-cheng-de-er-cha-shu-lcof/
 *
 * @author zeno
 */
public class J0028_IsSymmetric {

//请实现一个函数，用来判断一棵二叉树是不是对称的。如果一棵二叉树和它的镜像一样，那么它是对称的。
//
// 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
//
// 1
// / \
// 2 2
// / \ / \
//3 4 4 3
//但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:
//
// 1
// / \
// 2 2
// \ \
// 3 3
//
//
//
// 示例 1：
//
// 输入：root = [1,2,2,3,4,4,3]
//输出：true
//
//
// 示例 2：
//
// 输入：root = [1,2,2,null,3,null,3]
//输出：false
//
//
//
// 限制：
//
// 0 <= 节点个数 <= 1000
//
// 注意：本题与主站 101 题相同：https://leetcode-cn.com/problems/symmetric-tree/
// Related Topics 树
// 👍 91 👎 0


  public static void main(String[] args) {
    Solution solution = new J0028_IsSymmetric().new Solution();

  }

  // 执行耗时:0 ms,击败了100.00% 的Java用户
//leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode(int x) { val = x; } }
   */
  class Solution {

    public boolean isSymmetric(TreeNode root) {
      return isSymmetric0(root, root);
    }

    private boolean isSymmetric0(TreeNode root, TreeNode mirror) {
      if (root == null && mirror == null) {
        return true;
      }
      if (root == null || mirror == null) {
        return false;
      }
      if (root.val != mirror.val) {
        return false;
      }
      return isSymmetric0(root.left, mirror.right) && isSymmetric0(root.right, mirror.left);
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
