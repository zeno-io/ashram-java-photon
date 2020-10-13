package xyz.flysium.photon.algorithm.tree.bst;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import xyz.flysium.photon.tree.TreeNode;

/**
 * 173. 二叉搜索树迭代器
 * <p>
 * https://leetcode-cn.com/problems/binary-search-tree-iterator/
 *
 * @author zeno
 */
public interface T0173_BinarySearchTreeIterator {

  // 实现一个二叉搜索树迭代器。你将使用二叉搜索树的根节点初始化迭代器。
  //
  // 调用 next() 将返回二叉搜索树中的下一个最小的数。

  // 22ms
  class BSTIterator {

    private final Iterator<Integer> it;

    public BSTIterator(TreeNode root) {
      ArrayList<Integer> ans = new ArrayList<>();
      inorderTraversal(root, ans);
      it = ans.iterator();
    }

    private void inorderTraversal(TreeNode root, List<Integer> ans) {
      if (root == null) {
        return;
      }
      this.inorderTraversal(root.left, ans);
      ans.add(root.val);
      this.inorderTraversal(root.right, ans);
    }

    /**
     * @return the next smallest number
     */
    public int next() {
      return it.next();
    }

    /**
     * @return whether we have a next smallest number
     */
    public boolean hasNext() {
      return it.hasNext();
    }

  }

/**
 * Your BSTIterator object will be instantiated and called as such:
 * BSTIterator obj = new BSTIterator(root);
 * int param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */

}
