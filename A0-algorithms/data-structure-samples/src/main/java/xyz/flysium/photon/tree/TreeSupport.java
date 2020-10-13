package xyz.flysium.photon.tree;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import xyz.flysium.photon.RandomSupport;

/**
 * TODO description
 *
 * @author zeno
 */
public final class TreeSupport {

  private TreeSupport() {
  }

  /**
   * 生成一个二叉树
   *
   * @param val 层序遍历的值列表，如果为空，则为null
   * @return 二叉树的根结点
   */
  public static TreeNode newBinaryTree(Integer... val) {
    return (TreeNode) BINARY_TREE_CODEC.deserialize(val, TreeNode.class, false);
  }

  /**
   * 生成一个二叉搜索树 (BST)
   *
   * @param maxLevel 最大树高度
   * @param maxValue 最大值
   * @return 二叉搜索树的根结点
   */
  public static TreeNode generateRandomBinarySearchTree(int maxLevel, int maxValue) {
    return generateForBinarySearchTree(1, maxLevel, maxValue);
  }

  private static TreeNode generateForBinarySearchTree(int level, int maxLevel, int maxValue) {
    if (level > maxLevel || RandomSupport.randomValue(100) < 50) {
      return null;
    }
    TreeNode head = new TreeNode(RandomSupport.randomValue(maxValue));
    head.left = generateForBinarySearchTree(level + 1, maxLevel, maxValue);
    head.right = generateForBinarySearchTree(level + 1, maxLevel, maxValue);
    return head;
  }

  /**
   * 层序遍历二叉树
   *
   * @param root     二叉树的根结点
   * @param nullChar 如果为空，打印的字符串
   * @return 遍历结果
   */
  public static String[] toStrings(TreeNode root, String nullChar) {
    Integer[] serialize = BINARY_TREE_CODEC.serialize(root, true);
    String[] ans = new String[serialize.length];
    for (int i = 0; i < serialize.length; i++) {
      ans[i] = (serialize[i] == null) ? nullChar : Integer.toString(serialize[i]);
    }
    return ans;
  }

  private static final BinaryTreeCodec BINARY_TREE_CODEC = new BinaryTreeCodec();

  private static class BinaryTreeCodec {

    // Encodes a tree to a single string.
    public Integer[] serialize(TreeNode root, boolean levelEndToNull) {
      if (root == null) {
        return new Integer[]{null};
      }
      // 层序遍历，中间有 null 也记录
      List<Integer> l = new LinkedList<>();

      Deque<TreeNode> q = new LinkedList<>();
      TreeNode node = null;
      TreeNode currEnd = null;
      TreeNode nextEnd = null;

      q.offerLast(root);
      l.add(root.val);
      currEnd = root;
      while (!q.isEmpty()) {
        node = q.pollFirst();
        if (node.left != null) {
          q.offerLast(node.left);
          l.add(node.left.val);
          nextEnd = node.left;
        } else {
          l.add(null);
        }
        if (node.right != null) {
          q.offerLast(node.right);
          l.add(node.right.val);
          nextEnd = node.right;
        } else {
          l.add(null);
        }
        if (node == currEnd) {
          if (levelEndToNull) {
            l.add(null);
          }
          currEnd = nextEnd;
        }
      }
      Integer[] ans = new Integer[l.size()];
      for (int i = 0; i < ans.length; i++) {
        ans[i] = l.get(i);
      }
      return ans;
    }

    // Decodes your encoded data to tree.
    public <T> T deserialize(Integer[] data,
      Class<? extends TreeNode> clazz,
      boolean levelEndToNull) {
      if (data.length == 1 && data[0] == null) {
        return null;
      }
      Deque<TreeNode> list = new LinkedList<>();
      for (Integer s : data) {
        if (s == null) {
          list.offerLast(null);
        } else {
          try {
            list.offerLast(clazz.getConstructor(int.class).newInstance(s));
          } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
          }
        }
      }
      TreeNode root = list.pollFirst();
      Deque<TreeNode> q = new LinkedList<>();
      TreeNode node = null;
      TreeNode currEnd = null;
      TreeNode nextEnd = null;

      q.offerLast(root);
      currEnd = root;
      while (!q.isEmpty()) {
        node = q.pollFirst();
        node.left = list.pollFirst();
        node.right = list.pollFirst();
        if (node.left != null) {
          q.offerLast(node.left);
          nextEnd = node.left;
        }
        if (node.right != null) {
          q.offerLast(node.right);
          nextEnd = node.right;
        }
        if (node == currEnd) {
          if (levelEndToNull) {
            list.pollFirst();
          }
          currEnd = nextEnd;
        }
      }
      return (T) root;
    }

  }

  static class BSTIterator {

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
}
