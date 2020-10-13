package xyz.flysium.photon.algorithm.array.dimensional.easy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 118. 杨辉三角
 * <p>
 * https://leetcode-cn.com/problems/pascals-triangle/
 *
 * @author zeno
 */
public class T0118_PascalsTriangle {

  // 给定一个非负整数 numRows，生成杨辉三角的前 numRows 行。
  // 在杨辉三角中，每个数是它左上方和右上方的数的和。
  static class Solution {

    public List<List<Integer>> generate(int numRows) {
      LinkedList<List<Integer>> ans = new LinkedList<>();

      List<Integer> lastRow = null;
      for (int row = 1; row <= numRows; row++) {
        List<Integer> currRow = new ArrayList<>(row);
        currRow.add(1);
        if (row != 1) {
          for (int i = 1; i < row - 1; i++) {
            currRow.add(lastRow.get(i - 1) + lastRow.get(i));
          }
          currRow.add(1);
        }
        ans.addLast(currRow);
        lastRow = currRow;
      }
      return ans;
    }

  }

}
