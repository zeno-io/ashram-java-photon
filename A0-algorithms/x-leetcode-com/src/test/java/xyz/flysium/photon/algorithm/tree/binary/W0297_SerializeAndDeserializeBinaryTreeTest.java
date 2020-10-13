package xyz.flysium.photon.algorithm.tree.binary;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.TreeSupport;
import xyz.flysium.photon.tree.TreeNode;

/**
 * TODO description
 *
 * @author zeno
 */
public class W0297_SerializeAndDeserializeBinaryTreeTest {

  @Test
  public void test() {
    W0297_SerializeAndDeserializeBinaryTree.Codec ser = new W0297_SerializeAndDeserializeBinaryTree.Codec();
    W0297_SerializeAndDeserializeBinaryTree.Codec deser = new W0297_SerializeAndDeserializeBinaryTree.Codec();
    TreeNode root = null;
    String[] actual = null;
    String[] expects = null;

    root = TreeSupport.newBinaryTree(1, 2, 3, null, null, 4, 5);
    expects = TreeSupport.toStrings(root, "#");
    actual = TreeSupport.toStrings(deser.deserialize(ser.serialize(root)), "#");
    Assert.assertArrayEquals(expects, actual);
  }

  @Test
  public void test1() {
    W0297_SerializeAndDeserializeBinaryTree_1.Codec ser = new W0297_SerializeAndDeserializeBinaryTree_1.Codec();
    W0297_SerializeAndDeserializeBinaryTree_1.Codec deser = new W0297_SerializeAndDeserializeBinaryTree_1.Codec();
    TreeNode root = null;
    String[] actual = null;
    String[] expects = null;

    root = TreeSupport.newBinaryTree(1, 2, 3, null, null, 4, 5);
    expects = TreeSupport.toStrings(root, "#");
    actual = TreeSupport.toStrings(deser.deserialize(ser.serialize(root)), "#");
    Assert.assertArrayEquals(expects, actual);
  }

  @Test
  public void test2() {
    W0297_SerializeAndDeserializeBinaryTree_2.Codec ser = new W0297_SerializeAndDeserializeBinaryTree_2.Codec();
    W0297_SerializeAndDeserializeBinaryTree_2.Codec deser = new W0297_SerializeAndDeserializeBinaryTree_2.Codec();
    TreeNode root = null;
    String[] actual = null;
    String[] expects = null;

    root = TreeSupport.newBinaryTree(1, 2, 3, null, null, 4, 5);
    expects = TreeSupport.toStrings(root, "#");
    actual = TreeSupport.toStrings(deser.deserialize(ser.serialize(root)), "#");
    Assert.assertArrayEquals(expects, actual);
  }

}
