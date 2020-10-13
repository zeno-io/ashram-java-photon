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
public class T0119_PascalsTriangleII {

  // 给定一个非负索引 k，其中 k ≤ 33，返回杨辉三角的第 k 行。
  static class Solution {

    public List<Integer> getRow(int rowIndex) {
      final int numRows = rowIndex + 1;
      List<Integer> lastRow = new ArrayList<>(numRows);
      int[] currRow = new int[numRows];
      for (int row = 1; row <= numRows; row++) {
        currRow[0] = 1;
        currRow[row - 1] = 1;
        if (row != 1) {
          int i = 1;
          while (i < row - 1 && i <= row >> 1) {
            currRow[i] = lastRow.get(i - 1) + lastRow.get(i);
            i++;
          }
//          while (i < row - 1) {
//            currRow[i] = currRow[row - i - 1];
//            i++;
//          }
        }
//        for (int i = 0; i < lastRow.size(); i++) {
//          lastRow.set(i, currRow[i]);
//        }
        int k = 0;
        while (k < row - 1 && k <= row >> 1) {
          lastRow.set(k, currRow[k]);
          k++;
        }
        while (k < row - 1) {
          lastRow.set(k, currRow[row - k - 1]);
          k++;
        }
        lastRow.add(1);
      }
      return lastRow;
    }

  }

}
