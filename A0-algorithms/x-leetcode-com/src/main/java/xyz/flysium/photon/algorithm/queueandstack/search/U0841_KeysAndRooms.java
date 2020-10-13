package xyz.flysium.photon.algorithm.queueandstack.search;

import java.util.List;

/**
 * 841. 钥匙和房间
 * <p>
 * https://leetcode-cn.com/problems/keys-and-rooms/
 *
 * @author zeno
 */
public interface U0841_KeysAndRooms {

  // 有 N 个房间，开始时你位于 0 号房间。每个房间有不同的号码：0，1，2，...，N-1，并且房间里可能有一些钥匙能使你进入下一个房间。
  //
  //在形式上，对于每个房间 i 都有一个钥匙列表 rooms[i]，每个钥匙 rooms[i][j] 由 [0,1，...，N-1] 中的一个整数表示，
  // 其中 N = rooms.length。 钥匙 rooms[i][j] = v 可以打开编号为 v 的房间。
  //
  //最初，除 0 号房间外的其余所有房间都被锁住。
  //
  //你可以自由地在房间之间来回走动。
  //
  //如果能进入每个房间返回 true，否则返回 false。
  //

  // 1ms 94.41%
  // DFS
  class Solution {

    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
      final int n = rooms.size();
      boolean[] unlock = new boolean[n];
      boolean b = canVisitAllRooms(rooms, unlock, 0);
      return b && canVisitAllRooms(unlock);
    }

    private boolean canVisitAllRooms(List<List<Integer>> rooms, final boolean[] unlock, int start) {
      if (unlock[start]) {
        return true;
      }
      unlock[start] = true;
      List<Integer> keys = rooms.get(start);
      int falseCount = 0;
      int numbers = 0;
      for (Integer key : keys) {
        if (key == start) {
          continue;
        }
        numbers++;
        boolean b = canVisitAllRooms(rooms, unlock, key);
        if (!b) {
          falseCount++;
        }
      }
      if (numbers == 0) {
        return true;
      }
      return numbers > falseCount;
    }

    private boolean canVisitAllRooms(boolean[] unlock) {
      for (boolean b : unlock) {
        if (!b) {
          return false;
        }
      }
      return true;
    }

  }

}
