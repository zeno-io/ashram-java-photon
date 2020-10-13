package xyz.flysium.photon.algorithm.array.prefix.medium;

/**
 * 304. 二维区域和检索 - 矩阵不可变
 * <p>
 * https://leetcode-cn.com/problems/range-sum-query-2d-immutable/
 *
 * @author zeno
 */
public class T0304_RangeSumQuery2dImmutable {

  /**
   * Your NumMatrix object will be instantiated and called as such:
   * <p>
   * NumMatrix obj = new NumMatrix(matrix);
   * <p>
   * int param_1 = obj.sumRegion(row1,col1,row2,col2);
   */
  static class NumMatrix {

    // matrixSums(x,y) =   nums(0,0) + nums(0,1)+ nums(0,2) + ...+ nums(0,y)
    //             + nums(1,0) + nums(1,1)+ nums(1,2) + ...+ nums(1,y)
    //             + nums(2,0) + nums(2,1)+ nums(2,2) + ...+ nums(2,y)
    //             + ...
    //             + nums(x,0) + nums(x,1)+ nums(x,2) + ...+ nums(x,y)
    private final int[][] matrixSums;

    public NumMatrix(int[][] matrix) {
      // matrixSums (x,y) = matrixSums (x-1,y)+ matrixSums(x,y-1) - matrixSums (x-1,y-1) + nums(x,y)
      this.matrixSums = matrix;
      for (int x = 0; x < matrix.length; x++) {
        for (int y = 0; y < matrix[x].length; y++) {
          matrixSums[x][y] = (x == 0 ? 0 : matrixSums[x - 1][y])
            + (y == 0 ? 0 : matrixSums[x][y - 1])
            - (x == 0 || y == 0 ? 0 : matrixSums[x - 1][y - 1])
            + matrix[x][y];
        }
      }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
      return matrixSums[row2][col2]
        - (row1 == 0 ? 0 : matrixSums[row1 - 1][col2])
        - (col1 == 0 ? 0 : matrixSums[row2][col1 - 1])
        + (row1 == 0 || col1 == 0 ? 0 : matrixSums[row1 - 1][col1 - 1]);
    }

  }

}
