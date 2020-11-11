package xyz.flysium.photon.jianzhioffer.search.easy;

import xyz.flysium.photon.tree.TreeNode;

/**
 * 剑指 Offer 55 - II. 平衡二叉树
 * <p>
 * https://leetcode-cn.com/problems/ping-heng-er-cha-shu-lcof/
 *
 * @author zeno
 */
public class J0055_2_IsBalanced {

//输入一棵二叉树的根节点，判断该树是不是平衡二叉树。如果某二叉树中任意节点的左右子树的深度相差不超过1，那么它就是一棵平衡二叉树。
//
//
//
// 示例 1:
//
// 给定二叉树 [3,9,20,null,null,15,7]
//
//     3
//   / \
//  9  20
//    /  \
//   15   7
//
// 返回 true 。
//
//示例 2:
//
// 给定二叉树 [1,2,2,3,3,null,null,4,4]
//
//        1
//      / \
//     2   2
//    / \
//   3   3
//  / \
// 4   4
//
//
// 返回 false 。
//
//
//
// 限制：
//
//
// 1 <= 树的结点个数 <= 10000
//
//
// 注意：本题与主站 110 题相同：https://leetcode-cn.com/problems/balanced-binary-tree/
//
//
// Related Topics 树 深度优先搜索
// 👍 78 👎 0


  public static void main(String[] args) {
    Solution solution = new J0055_2_IsBalanced().new Solution();

  }

  // 执行耗时:1 ms,击败了99.98% 的Java用户
//leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode(int x) { val = x; } }
   */
  class Solution {

    public boolean isBalanced(TreeNode root) {
      if (root == null) {
        return true;
      }
      MyInfo info = isBalanced0(root);
      return info.isBalanced;
    }

    private MyInfo isBalanced0(TreeNode root) {
      if (root == null) {
        return new MyInfo(true, 0);
      }
      MyInfo l = isBalanced0(root.left);
      MyInfo r = isBalanced0(root.right);
      boolean b = l.isBalanced && r.isBalanced && Math.abs(l.maxDepth - r.maxDepth) <= 1;
      return new MyInfo(b, Math.max(l.maxDepth, r.maxDepth) + 1);
    }

    class MyInfo {

      boolean isBalanced;
      int maxDepth;

      public MyInfo(boolean isBalanced, int maxDepth) {
        this.isBalanced = isBalanced;
        this.maxDepth = maxDepth;
      }
    }
  }
//leetcode submit region end(Prohibit modification and deletion)


}
