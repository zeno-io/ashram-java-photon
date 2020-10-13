package xyz.flysium.photon.algorithm.array.dimensional.easy;

/**
 * 661. 图片平滑器
 * <p>
 * https://leetcode-cn.com/problems/image-smoother/
 *
 * @author zeno
 */
public class T0661_ImageSmoother {

  //  包含整数的二维矩阵 M 表示一个图片的灰度。你需要设计一个平滑器来让每一个单元的灰度成为平均灰度 (向下舍入) ，
  //  平均灰度的计算是周围的8个单元和它本身的值求平均，如果周围的单元格不足八个，则尽可能多的利用它们。
  //  给定矩阵中的整数范围为 [0, 255]。
  //  矩阵的长和宽的范围均为 [1, 150]。
  static class Solution {

    public int[][] imageSmoother(int[][] M) {
      int[] curr = new int[M[0].length];
      int[] last = new int[M[0].length];
      for (int x = 0; x < M.length; x++) {
        System.arraycopy(M[x], 0, curr, 0, M[0].length);
        for (int y = 0; y < M[0].length; y++) {
          M[x][y] = avg(M, last, curr, x, y);
        }
        System.arraycopy(curr, 0, last, 0, M[0].length);
      }
      return M;
    }

    private int avg(int[][] M, int[] last, int[] curr, int x, int y) {
      int sum = M[x][y];
      int cnt = 1;
      boolean hx0 = x > 0;
      boolean hy0 = y > 0;
      boolean hx = x < M.length - 1;
      boolean hy = y < M[0].length - 1;
      if (hy0) {
        sum += curr[y - 1];// M[x][y - 1];
        cnt++;
      }
      if (hy) {
        sum += M[x][y + 1];
        cnt++;
      }
      if (hx0) {
        sum += last[y];// M[x - 1][y];
        cnt++;
      }
      if (hx) {
        sum += M[x + 1][y];
        cnt++;
      }
      if (hx0 && hy0) {
        sum += last[y - 1];// M[x - 1][y - 1];
        cnt++;
      }
      if (hx0 && hy) {
        sum += last[y + 1];// M[x - 1][y + 1];
        cnt++;
      }
      if (hx && hy0) {
        sum += M[x + 1][y - 1];
        cnt++;
      }
      if (hx && hy) {
        sum += M[x + 1][y + 1];
        cnt++;
      }
      return sum / cnt;
    }

  }

}
