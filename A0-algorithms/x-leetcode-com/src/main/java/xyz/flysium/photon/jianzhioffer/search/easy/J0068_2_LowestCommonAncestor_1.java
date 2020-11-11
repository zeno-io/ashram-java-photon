package xyz.flysium.photon.jianzhioffer.search.easy;

import xyz.flysium.photon.tree.TreeNode;

/**
 * 剑指 Offer 68 - II. 二叉树的最近公共祖先
 * <p>
 * https://leetcode-cn.com/problems/er-cha-shu-de-zui-jin-gong-gong-zu-xian-lcof/
 *
 * @author zeno
 */
public class J0068_2_LowestCommonAncestor_1 {

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
    Solution solution = new J0068_2_LowestCommonAncestor_1().new Solution();

  }

  // 	执行用时：8 ms, 在所有 Java 提交中击败了57.69% 的用户
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

    // 最近公共祖先的定义： 设节点 root 为节点 p,q 的某公共祖先，
    // 若其左子节点 root.left 和右子节点 root.right 都不是 p,q 的公共祖先，
    // 则称 root  是 “最近的公共祖先” 。
    // 如果 root 是 p, q 的最近公共祖先，只可能是以下情况之一：
    //1. p, q 在 root的子树上，且分列root的两侧
    //2. p = root, 且 q 在 root 的左子树或右子树
    //3. q = root, 且 p 在 root 的左子树或右子树
    private TreeNode lowestCommonAncestor0(TreeNode root, TreeNode p, TreeNode q) {
      if (root == null || root == p || root == q) {
        return root;
      }
      TreeNode l = lowestCommonAncestor0(root.left, p, q);
      TreeNode r = lowestCommonAncestor0(root.right, p, q);
      // p, q 都不在 root 节点的左子树, null
      if (l == null && r == null) {
        return null;
      }
      // p, q 在 root 的子树上，且分列 root 的两侧, 说明当前节点即为公共祖先，返回当前节点。
      if (l != null && r != null) {
        return root;
      }
      // 当左子树返回空，则说明p、q节点不在当前节点的左子树，此时返回右子树的返回值
      if (l == null) {
        return r;
      }
      // 当右子树返回空，则说明p、q节点不在当前节点的右子树，此时返回左子树的返回值
      return l;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
