package xyz.flysium.photon.tree;

/**
 * TODO description
 *
 * @author zeno
 */
public interface ITreeNode {

  int getVal();

  void setVal(int val);

  ITreeNode getLeft();

  void setLeft(ITreeNode left);

  ITreeNode getRight();

  void setRight(ITreeNode right);

}
