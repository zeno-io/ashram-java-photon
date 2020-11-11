package xyz.flysium.photon.jianzhioffer.search.easy;

import xyz.flysium.photon.tree.TreeNode;

/**
 * 剑指 Offer 68 - I. 二叉搜索树的最近公共祖先
 * <p>
 * https://leetcode-cn.com/problems/er-cha-sou-suo-shu-de-zui-jin-gong-gong-zu-xian-lcof/
 *
 * @author zeno
 */
public class J0068_1_LowestCommonAncestor {

//给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
//
// 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（
//一个节点也可以是它自己的祖先）。”
//
// 例如，给定如下二叉搜索树: root = [6,2,8,0,4,7,9,null,null,3,5]
//
//
//
//
//
// 示例 1:
//
// 输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
//输出: 6
//解释: 节点 2 和节点 8 的最近公共祖先是 6。
//
//
// 示例 2:
//
// 输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
//输出: 2
//解释: 节点 2 和节点 4 的最近公共祖先是 2, 因为根据定义最近公共祖先节点可以为节点本身。
//
//
//
// 说明:
//
//
// 所有节点的值都是唯一的。
// p、q 为不同节点且均存在于给定的二叉搜索树中。
//
//
// 注意：本题与主站 235 题相同：https://leetcode-cn.com/problems/lowest-common-ancestor-of-a
//-binary-search-tree/
// Related Topics 树
// 👍 64 👎 0


  public static void main(String[] args) {
    Solution solution = new J0068_1_LowestCommonAncestor().new Solution();

  }

  // 执行耗时:6 ms,击败了100.00% 的Java用户
//leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode(int x) { val = x; } }
   */
  class Solution {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
      if (root == null) {
        return null;
      }
      if (p.val > q.val) {
        return lowestCommonAncestor0(root, q, p);
      }
      return lowestCommonAncestor0(root, p, q);
    }

    private TreeNode lowestCommonAncestor0(TreeNode root, TreeNode p, TreeNode q) {
      if (root == null) {
        return null;
      }
      if (q.val < root.val) {
        return lowestCommonAncestor0(root.left, p, q);
      } else if (p.val > root.val) {
        return lowestCommonAncestor0(root.right, p, q);
      }
      return root;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
