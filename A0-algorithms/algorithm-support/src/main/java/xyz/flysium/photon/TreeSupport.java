package xyz.flysium.photon;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import xyz.flysium.photon.tree.ITreeNode;
import xyz.flysium.photon.tree.Node;
import xyz.flysium.photon.tree.TreeNode;

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
   * 生成一个二叉树 (节点带有 next 指针)
   *
   * @param val 层序遍历的值列表，如果为空，则为null
   * @return 二叉树的根结点
   */
  public static Node newBinaryTree2(Integer... val) {
    return (Node) BINARY_TREE_CODEC.deserialize(val, Node.class, false);
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

  /**
   * 层序遍历二叉树 (节点带有 next 指针)
   *
   * @param root     二叉树的根结点
   * @param nullChar 如果为空，打印的字符串
   * @return 遍历结果
   */
  public static String[] toStrings(Node root, String nullChar) {
    Integer[] serialize = BINARY_TREE_CODEC.serialize(root, true);
    String[] ans = new String[serialize.length];
    for (int i = 0; i < serialize.length; i++) {
      ans[i] = (serialize[i] == null) ? nullChar : Integer.toString(serialize[i]);
    }
    return ans;
  }

  public static String[] toStringsByNextPointers(Node root, String levelChar) {
    if (root == null) {
      return new String[0];
    }
    List<String> l = new LinkedList<>();
    Node curr = root;

    while (curr != null) {
      Node nextLevelStartDummy = new Node(0);
      Node ls = nextLevelStartDummy;
      while (curr != null) {
        l.add(Integer.toString(curr.val));
        if (ls.next == null && (curr.left != null || curr.right != null)) {
          ls.next = (curr.left != null) ? curr.left : curr.right;
        }
        curr = curr.next;
      }
      if (levelChar != null) {
        l.add(levelChar);
      }
      if (ls.next == null) {
        break;
      }
      // go to nextLevelStart
      curr = nextLevelStartDummy.next;
    }

    String[] ans = new String[l.size()];
    for (int i = 0; i < ans.length; i++) {
      ans[i] = l.get(i);
    }
    return ans;
  }

  private static final BinaryTreeCodec BINARY_TREE_CODEC = new BinaryTreeCodec();

  private static class BinaryTreeCodec {

    // Encodes a tree to a single string.
    public Integer[] serialize(ITreeNode root, boolean levelEndToNull) {
      if (root == null) {
        return new Integer[]{null};
      }
      // 层序遍历，中间有 null 也记录
      List<Integer> l = new LinkedList<>();

      Deque<ITreeNode> q = new LinkedList<>();
      ITreeNode node = null;
      ITreeNode currEnd = null;
      ITreeNode nextEnd = null;

      q.offerLast(root);
      l.add(root.getVal());
      currEnd = root;
      while (!q.isEmpty()) {
        node = q.pollFirst();
        if (node.getLeft() != null) {
          q.offerLast(node.getLeft());
          l.add(node.getLeft().getVal());
          nextEnd = node.getLeft();
        } else {
          l.add(null);
        }
        if (node.getRight() != null) {
          q.offerLast(node.getRight());
          l.add(node.getRight().getVal());
          nextEnd = node.getRight();
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
      Class<? extends ITreeNode> clazz,
      boolean levelEndToNull) {
      if (data.length == 1 && data[0] == null) {
        return null;
      }
      Deque<ITreeNode> list = new LinkedList<>();
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
      ITreeNode root = list.pollFirst();
      Deque<ITreeNode> q = new LinkedList<>();
      ITreeNode node = null;
      ITreeNode currEnd = null;
      ITreeNode nextEnd = null;

      q.offerLast(root);
      currEnd = root;
      while (!q.isEmpty()) {
        node = q.pollFirst();
        node.setLeft(list.pollFirst());
        node.setRight(list.pollFirst());
        if (node.getLeft() != null) {
          q.offerLast(node.getLeft());
          nextEnd = node.getLeft();
        }
        if (node.getRight() != null) {
          q.offerLast(node.getRight());
          nextEnd = node.getRight();
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
