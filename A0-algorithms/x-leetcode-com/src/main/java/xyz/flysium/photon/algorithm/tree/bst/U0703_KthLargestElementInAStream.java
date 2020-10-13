package xyz.flysium.photon.algorithm.tree.bst;

/**
 * 703. 数据流中的第K大元素
 * <p>
 * https://leetcode-cn.com/problems/kth-largest-element-in-a-stream/
 *
 * @author zeno
 */
public interface U0703_KthLargestElementInAStream {

  // 设计一个找到数据流中第K大元素的类（class）。
  // 注意是排序后的第K大元素，不是第K个不同的元素。
  //
  // 你的 KthLargest 类需要一个同时接收整数 k 和整数数组nums 的构造器，它包含数据流中的初始元素。
  // 每次调用 KthLargest.add，返回当前数据流中第K大的元素。
  //

  //  你可以假设 nums 的长度≥ k-1 且k ≥ 1。

  // 449 ms
  class KthLargest {

    private final int k;
    private final BST bst = new BST();

    public KthLargest(int k, int[] nums) {
      this.k = k;
      for (int num : nums) {
        bst.add(num);
      }
    }

    public int add(int val) {
      bst.add(val);
      return bst.search(k).val;
    }

    static class BST {

      private MyTreeNode root;

      void add(int val) {
        root = add(root, val);
      }

      private MyTreeNode add(MyTreeNode node, int val) {
        if (node == null) {
          MyTreeNode n = new MyTreeNode(val);
          n.cnt = 1;
          return n;
        }
        if (val < node.val) {
          node.left = add(node.left, val);
        }
        if (val > node.val) {
          node.right = add(node.right, val);
        }
        // 元素重复 不添加进树但是count++
        node.cnt++;
        return node;
      }

      MyTreeNode search(int k) {
        return search(root, k);
      }

      private MyTreeNode search(MyTreeNode node, int k) {
        if (node == null) {
          return null;
        }
        int leftNodeCount = (node.left == null) ? 0 : node.left.cnt;
        int rightNodeCount = (node.right == null) ? 0 : node.right.cnt;
        int currNodeCount = node.cnt - leftNodeCount - rightNodeCount;
        if (k > currNodeCount + rightNodeCount) {
          // k > 当前结点数加右子树的结点数，则搜索左子树
          return search(node.left, k - currNodeCount - rightNodeCount);
        } else if (k <= rightNodeCount) {
          // k <= 右子树的结点数，则搜索右子树
          return search(node.right, k);
        } else {
          // k == 当前结点数加右子树的结点数，则找到第k大的结点
          return node;
        }
      }

    }

    static class MyTreeNode {

      int val;
      int cnt = 1;
      MyTreeNode left;
      MyTreeNode right;

      public MyTreeNode(int val) {
        this.val = val;
      }
    }

  }

/**
 * Your KthLargest object will be instantiated and called as such:
 * KthLargest obj = new KthLargest(k, nums);
 * int param_1 = obj.add(val);
 */

}
