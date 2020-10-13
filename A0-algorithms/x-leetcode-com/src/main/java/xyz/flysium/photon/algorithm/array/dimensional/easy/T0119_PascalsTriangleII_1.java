package xyz.flysium.photon.algorithm.array.dimensional.easy;

import java.util.ArrayList;
import java.util.List;

/**
 * 119. 杨辉三角II
 * <p>
 * https://leetcode-cn.com/problems/pascals-triangle-ii/
 *
 * @author zeno
 */
public class T0119_PascalsTriangleII_1 {

  // 给定一个非负索引 k，其中 k ≤ 33，返回杨辉三角的第 k 行。
  static class Solution {

    public List<Integer> getRow(int rowIndex) {
      final int numRows = rowIndex + 1;
      if (numRows == 1) {
        List<Integer> ans = new ArrayList<>(numRows);
        ans.add(1);
        return ans;
      }
      if (numRows == 2) {
        List<Integer> ans = new ArrayList<>(numRows);
        ans.add(1);
        ans.add(1);
        return ans;
      }
      if (numRows == 3) {
        List<Integer> ans = new ArrayList<>(numRows);
        ans.add(1);
        ans.add(2);
        ans.add(1);
        return ans;
      }
      if (numRows == 4) {
        List<Integer> ans = new ArrayList<>(numRows);
        ans.add(1);
        ans.add(3);
        ans.add(3);
        ans.add(1);
        return ans;
      }
      List<Integer> lastRow = new ArrayList<>(numRows);
      int[] currRow = new int[numRows];
      for (int row = 1; row <= numRows; row++) {
        currRow[0] = 1;
        currRow[row - 1] = 1;
        if (row != 1) {
          for (int i = 1; i < row - 1; i++) {
            currRow[i] = lastRow.get(i - 1) + lastRow.get(i);
          }
        }
        for (int i = 0; i < lastRow.size(); i++) {
          lastRow.set(i, currRow[i]);
        }
        lastRow.add(1);
      }
      return lastRow;
    }

  }

}
