package xyz.flysium.photon.jianzhioffer.search.easy;

import xyz.flysium.photon.tree.TreeNode;

/**
 * 剑指 Offer 68 - II. 二叉树的最近公共祖先
 * <p>
 * https://leetcode-cn.com/problems/er-cha-shu-de-zui-jin-gong-gong-zu-xian-lcof/
 *
 * @author zeno
 */
public class J0068_2_LowestCommonAncestor {

//给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
//
// 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（
//一个节点也可以是它自己的祖先）。”
//
// 例如，给定如下二叉树: root = [3,5,1,6,2,0,8,null,null,7,4]
//
//
//
//
//
// 示例 1:
//
// 输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
//输出: 3
//解释: 节点 5 和节点 1 的最近公共祖先是节点 3。
//
//
// 示例 2:
//
// 输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
//输出: 5
//解释: 节点 5 和节点 4 的最近公共祖先是节点 5。因为根据定义最近公共祖先节点可以为节点本身。
//
//
//
//
// 说明:
//
//
// 所有节点的值都是唯一的。
// p、q 为不同节点且均存在于给定的二叉树中。
//
//
// 注意：本题与主站 236 题相同：https://leetcode-cn.com/problems/lowest-common-ancestor-of-a
//-binary-tree/
// Related Topics 树
// 👍 155 👎 0


  public static void main(String[] args) {
    Solution solution = new J0068_2_LowestCommonAncestor().new Solution();

  }

  // 	执行耗时:7 ms,击败了100.00% 的Java用户
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
      return lowestCommonAncestor0(root, p, q);
    }

    private TreeNode lowestCommonAncestor0(TreeNode root, TreeNode p, TreeNode q) {
      if (root == null) {
        return null;
      }
      // root 左子树中如果有 p, q 的公共祖先，则返回
      TreeNode l = lowestCommonAncestor0(root.left, p, q);
      if (l != null) {
        return l;
      }
      // root 右子树中如果有 p, q  的公共祖先，则返回
      TreeNode r = lowestCommonAncestor0(root.right, p, q);
      if (r != null) {
        return r;
      }
      // root 左，右子树没有 p, q  的公共祖先，检查当前节点是否 p, q 的公共祖先
      if (find(root, p) && find(root, q)) {
        return root;
      }
      // 当前节点 root 的树没有 p, q 的公共祖先
      return null;
    }

    private boolean find(TreeNode root, TreeNode x) {
      if (root == null) {
        return false;
      }
      if (root.val == x.val) {
        return true;
      }
      boolean b = find(root.left, x);
      if (b) {
        return true;
      }
      return find(root.right, x);
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
