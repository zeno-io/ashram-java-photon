package xyz.flysium.photon.jianzhioffer.search.easy;

import xyz.flysium.photon.tree.TreeNode;

/**
 * 剑指 Offer 27. 二叉树的镜像
 * <p>
 * https://leetcode-cn.com/problems/er-cha-shu-de-jing-xiang-lcof/
 *
 * @author zeno
 */
public class J0027_MirrorTree {

//请完成一个函数，输入一个二叉树，该函数输出它的镜像。
//
// 例如输入：
//
// 4
// / \
// 2 7
// / \ / \
//1 3 6 9
//镜像输出：
//
// 4
// / \
// 7 2
// / \ / \
//9 6 3 1
//
//
//
// 示例 1：
//
// 输入：root = [4,2,7,1,3,6,9]
//输出：[4,7,2,9,6,3,1]
//
//
//
//
// 限制：
//
// 0 <= 节点个数 <= 1000
//
// 注意：本题与主站 226 题相同：https://leetcode-cn.com/problems/invert-binary-tree/
// Related Topics 树
// 👍 75 👎 0


  public static void main(String[] args) {
    Solution solution = new J0027_MirrorTree().new Solution();

  }

  // 执行耗时:0 ms,击败了100.00% 的Java用户
//leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode(int x) { val = x; } }
   */
  class Solution {

    public TreeNode mirrorTree(TreeNode root) {
      if (root == null) {
        return null;
      }
      return mirror(root);
    }

    private TreeNode mirror(TreeNode root) {
      if (root == null) {
        return null;
      }
      TreeNode mirror = new TreeNode(root.val);
      mirror.left = mirror(root.right);
      mirror.right = mirror(root.left);
      return mirror;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)


}
