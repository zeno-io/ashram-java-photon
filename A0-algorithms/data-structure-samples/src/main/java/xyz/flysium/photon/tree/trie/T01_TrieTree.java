package xyz.flysium.photon.tree.trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 前缀树
 *
 * @author zeno
 */
public class T01_TrieTree {

  public static void main(String[] args) {
    boolean single = false;

    if (single) {
      TrieTree t = new TrieTree();
      t.insert("mpju");
      t.insert("g");
      t.insert("zjsdstcjdgrh");
      t.insert("es");
      t.delete("tlqmlae");
      t.insert("leqfntioxokej");
      t.delete("mpju");
      t.delete("xgvahmcpwfks");
      t.insert("zjsdstcjdgrh");
      t.insert("tlqmlae");
      t.insert("leqfntioxokej");
      System.out.println(t.prefixNumber("l"));//2
      return;
    }

    int times = 100000;
    int size = 10;
    int maxLength = 20;
    out:
    for (int i = 0; i < times; i++) {
      NativeButCorrectTrieTree t0 = new NativeButCorrectTrieTree();
      TrieTree t1 = new TrieTree();

      String[] ss = randomStringArray(size, maxLength);
      List<String> operations = new ArrayList<>(2 * size);
      for (int epoch = 0; epoch < 2; epoch++) {
        for (int j = 0; j < size; j++) {
          String s = ss[j];

          double decide = Math.random();
          if (decide < 0.25) {
            t0.insert(s);
            t1.insert(s);
            operations.add("t.insert(\"" + s + "\");\n");
          } else if (decide < 0.5) {
            t0.delete(s);
            t1.delete(s);
            operations.add("t.delete(\"" + s + "\");\n");
          } else if (decide < 0.75) {
            int ans1 = t0.search(s);
            int ans2 = t1.search(s);
            if (ans1 != ans2) {
              operations.add(" System.out.println(t.search(\"" + s + "\"));//" + ans1 + "\n");
              System.out.println("-> Wrong algorithm !!!");
              outputSpot(operations, ans1);
              break out;
            }
            operations.add("t.search(\"" + s + "\");\n");
          } else {
            int ans1 = t0.prefixNumber(s);
            int ans2 = t1.prefixNumber(s);
            if (ans1 != ans2) {
              operations.add("System.out.println(t.prefixNumber(\"" + s + "\"));//" + ans1 + "\n");
              System.out.println("-> Wrong algorithm !!!");
              outputSpot(operations, ans1);
              break out;
            }
            operations.add("t.prefixNumber(\"" + s + "\");\n");
          }
        }
      }
    }
    System.out.println("Finish !");
  }

  private static void outputSpot(List<String> operations, int ans1) {
    for (String operation : operations) {
      System.out.print(operation + " ");
    }
    System.out.println();
  }

  static class TrieTree {

    private final Node root;

    public TrieTree() {
      root = new Node('\0');
    }

    private static class Node {

      private final char c;
      private int pass;
      private int end;
      private final Map<Character, Node> next;

      public Node(char c) {
        this.c = c;
        this.pass = 0;
        this.end = 0;
        this.next = new HashMap<>();
      }

      public void setPass(int pass) {
        if (pass < 0) {
          this.pass = 0;
          return;
        }
        this.pass = pass;
      }

      public void setEnd(int end) {
        if (end < 0) {
          this.end = 0;
          return;
        }
        this.end = end;
      }
    }

    // 添加某个字符串，可以重复添加，每次算1个
    public void insert(String s) {
      if (s == null || s.isEmpty()) {
        return;
      }
      Node node = root;
      node.pass++;
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        node.next.putIfAbsent(c, new Node(c));
        node = node.next.get(c);
        node.pass++;
      }
      node.end++;
    }

    // 查询某个字符串在结构中还有几个
    public int search(String s) {
      if (s == null || s.isEmpty()) {
        return 0;
      }
      Node node = root;
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (!node.next.containsKey(c)) {
          return 0;
        }
        node = node.next.get(c);
      }
      return node.end;
    }

    // 查询有多少个字符串，是以 s 做前缀的
    public int prefixNumber(String s) {
      if (s == null || s.isEmpty()) {
        return 0;
      }
      Node node = root;
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (!node.next.containsKey(c)) {
          return 0;
        }
        node = node.next.get(c);
      }
      return node.pass;
    }

    // 删掉某个字符串，可以重复删除，每次算1个
    public void delete(String s) {
      if (s == null || s.isEmpty()) {
        return;
      }
      if (search(s) == 0) {
        return;
      }
      Node node = root;
      node.pass--;
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        // 如果 pass = 1 说明此路径中除了当前字符串，不存在其他可能
        // 既然删除当前字符串，那么这个节点路径可以移除
        if (node.next.get(c).pass == 1) {
          node.next.remove(c);
          return;
        }
        node = node.next.get(c);
        node.pass--;
      }
      node.end--;
    }
  }

  static class NativeButCorrectTrieTree {

    private final Map<String, Integer> hash;

    public NativeButCorrectTrieTree() {
      hash = new HashMap<>();
    }

    // 添加某个字符串，可以重复添加，每次算1个
    public void insert(String s) {
      if (s == null || s.isEmpty()) {
        return;
      }
      hash.putIfAbsent(s, 0);
      hash.put(s, hash.get(s) + 1);
    }

    // 查询某个字符串在结构中还有几个
    public int search(String s) {
      if (s == null || s.isEmpty()) {
        return 0;
      }
      return hash.getOrDefault(s, 0);
    }

    // 查询有多少个字符串，是以 s 做前缀的
    public int prefixNumber(String s) {
      if (s == null || s.isEmpty()) {
        return 0;
      }
      int count = 0;
      for (String key : hash.keySet()) {
        if (key.startsWith(s)) {
          count += hash.get(key);
        }
      }
      return count;
    }

    // 删掉某个字符串，可以重复删除，每次算1个
    public void delete(String s) {
      if (s == null || s.isEmpty()) {
        return;
      }
      if (hash.containsKey(s)) {
        Integer v = hash.get(s);
        if (v == 1) {
          hash.remove(s);
          return;
        }
        hash.put(s, v - 1);
      }
    }
  }

  private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

  private static String[] randomStringArray(int size, int maxLength) {
    String[] ss = new String[size];
    for (int i = 0; i < size; i++) {
      ss[i] = randomString(maxLength);
    }
    return ss;
  }

  private static String randomString(int maxLength) {
    int strLength = RANDOM.nextInt(maxLength);
    StringBuilder b = new StringBuilder();
    for (int k = 0; k < strLength; k++) {
      char c = (char) ('a' + RANDOM.nextInt(26));
      b.append(c);
    }
    return b.toString();
  }

}

