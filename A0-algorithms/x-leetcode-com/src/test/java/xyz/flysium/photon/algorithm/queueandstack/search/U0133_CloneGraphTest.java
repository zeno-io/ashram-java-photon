package xyz.flysium.photon.algorithm.queueandstack.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;
import xyz.flysium.photon.algorithm.queueandstack.search.basic.U0133_CloneGraph;
import xyz.flysium.photon.algorithm.queueandstack.search.basic.U0133_CloneGraph_1;

/**
 * TODO description
 *
 * @author zeno
 */
public class U0133_CloneGraphTest {

  @Test
  public void test() {
    U0133_CloneGraph.Solution solution = new U0133_CloneGraph.Solution();
    Node node = null;
    Node cloned = null;

    node = newGraph(ArraySupport.newTwoDimensionalArray("[[2,4],[1,3],[2,4],[1,3]]"));
    cloned = solution.cloneGraph(node);
    Assert.assertTrue(isCloned(node, cloned, new HashMap<>()));
  }

  @Test
  public void test1() {
    U0133_CloneGraph_1.Solution solution = new U0133_CloneGraph_1.Solution();
    Node node = null;
    Node cloned = null;

    node = newGraph(ArraySupport.newTwoDimensionalArray("[[2,4],[1,3],[2,4],[1,3]]"));
    cloned = solution.cloneGraph(node);
    Assert.assertTrue(isCloned(node, cloned, new HashMap<>()));
  }

  private Node newGraph(int[][] arr) {
    Node[] ns = new Node[arr.length];
    for (int i = 0; i < arr.length; i++) {
      ns[i] = new Node(i + 1);
    }
    for (int i = 0; i < arr.length; i++) {
      ns[i].neighbors = new ArrayList<>(arr[i].length);
      for (int j = 0; j < arr[i].length; j++) {
        ns[i].neighbors.add(ns[arr[i][j] - 1]);
      }
    }
    return ns[0];
  }

  private boolean isCloned(Node node, Node cloned, Map<Integer, Node> visited) {
    if (visited.containsKey(node.val)) {
      return visited.get(node.val) == cloned;
    }
    visited.putIfAbsent(node.val, cloned);
    if (node == cloned) {
      return false;
    }
    if (node.neighbors.size() != cloned.neighbors.size()) {
      return false;
    }
    for (int i = 0; i < node.neighbors.size(); i++) {
      if (node.neighbors.get(i) == null || cloned.neighbors.get(i) == null) {
        return false;
      }
      if (node.neighbors.get(i).val != cloned.neighbors.get(i).val) {
        return false;
      }
    }
    for (int i = 0; i < node.neighbors.size(); i++) {
      if (!isCloned(node.neighbors.get(i), cloned.neighbors.get(i), visited)) {
        return false;
      }
    }
    return true;
  }

}
