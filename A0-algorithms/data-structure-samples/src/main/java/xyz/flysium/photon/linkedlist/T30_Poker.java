package xyz.flysium.photon.linkedlist;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 手里有一副扑克牌。按照下列规则把他堆放桌上。
 * <p>
 * 一，拿出最上面的一张牌，放桌上，然后把接下来的一张牌放在扑克牌的最下面。循环，直到没有手牌。
 * <p>
 * 现在已知桌上牌的顺序。求原手牌的顺序。
 *
 * @author zeno
 */
public class T30_Poker {

  public static void main(String[] args) {
    String[] cards = new String[]{"红桃A", "红桃2", "红桃3", "红桃4", "红桃5", "红桃6", "红桃7", "红桃8", "红桃9",
      "红桃10", "红桃J", "红桃Q", "红桃K", "黑桃A", "黑桃2", "黑桃3", "黑桃4", "黑桃5", "黑桃6", "黑桃7", "黑桃8", "黑桃9",
      "黑桃10", "黑桃J", "黑桃Q", "黑桃K", "方块A", "方块2", "方块3", "方块4", "方块5", "方块6", "方块7", "方块8", "方块9",
      "方块10", "方块J", "方块Q", "方块K", "梅花A", "梅花2", "梅花3", "梅花4", "梅花5", "梅花6", "梅花7", "梅花8", "梅花9",
      "梅花10", "梅花J", "梅花Q", "梅花K", "小王", "大王"};
//    String[] cards = new String[]{"1", "2", "3", "4"};
    String[] out = new T30_Poker().flip(cards);
    String[] origin = new T30_Poker().origin(out);
    System.out.println(Arrays.toString(out));
    System.out.println(Arrays.toString(origin));
  }

  // 模拟翻转
  public String[] flip(String[] in) {
    final int length = in.length;
    // 桌子
    LinkedList<String> res = new LinkedList<>();
    // 手上
    LinkedList<String> list = new LinkedList<>();
    // 初始化
    for (String e : in) {
      list.addLast(e);
    }
    for (int i = 0; i < length; i++) {
      // 依次从手上取出一张牌，放到桌子上
      res.addLast(list.pollFirst());
      // 如果手上没有牌了，跳过
      if (list.size() > 0) {
        // 手上的牌取顶上的一张牌沉到最后去
        list.addLast(list.pollFirst());
      }
    }
    String[] ans = new String[length];
    res.toArray(ans);
    return ans;
  }

  public String[] origin(String[] out) {
    final int length = out.length;
    // 手上
    LinkedList<String> list = new LinkedList<>();
    // 桌子上
    LinkedList<String> res = new LinkedList<>();
    // 初始化
    for (String e : out) {
      res.addLast(e);
    }
    for (int i = 0; i < length; i++) {
      // 如果手上的牌只有一张牌时，不做翻转
      if (list.size() > 1) {
        // 从手上抽出最后一张牌，放在顶上
        list.addFirst(list.pollLast());
      }
      // 依次从桌子上拿回一张牌，放在手上
      list.addFirst(res.pollLast());
    }
    String[] ans = new String[length];
    list.toArray(ans);
    return ans;
  }

}
