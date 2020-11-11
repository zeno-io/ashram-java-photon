package xyz.flysium.photon.algorithm.queueandstack.search.basic;

import java.util.Arrays;
import java.util.List;

/**
 * 733. 图像渲染
 * <p>
 * https://leetcode-cn.com/problems/flood-fill/
 *
 * @author zeno
 */
public interface U0733_FloodFill {

  // 有一幅以二维整数数组表示的图画，每一个整数表示该图画的像素值大小，数值在 0 到 65535 之间。
  //
  //给你一个坐标 (sr, sc) 表示图像渲染开始的像素值（行 ，列）和一个新的颜色值 newColor，让你重新上色这幅图像。
  //
  //为了完成上色工作，从初始坐标开始，记录初始坐标的上下左右四个方向上像素值与初始坐标相同的相连像素点，接着再记录这四个方向上符合条件的像素点与他们对应四个方向上像素值与初始坐标相同的相连像素点，……，重复该过程。将所有有记录的像素点的颜色值改为新的颜色值。
  //
  //最后返回经过上色渲染后的图像。
  //

  // 1 ms, 98.48%
  class Solution {

    private static final List<int[]> DIRECTIONS = Arrays.asList(
      new int[]{0, -1},// up
      new int[]{0, 1}, // down
      new int[]{-1, 0}, // left
      new int[]{1, 0} // right
    );

    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
      dfs(image, sr, sc, image[sr][sc], newColor);
      return image;
    }

    private void dfs(int[][] image, int x, int y, final int initializeColor, final int newColor) {
      if (x < 0 || y < 0 || x >= image.length || y >= image[0].length) {
        return;
      }
      if (image[x][y] != initializeColor) {
        return;
      }
      if (image[x][y] == newColor) {
        return;
      }
      image[x][y] = newColor;
      for (int[] d : DIRECTIONS) {
        int x1 = x + d[0];
        int y1 = y + d[1];
        dfs(image, x1, y1, initializeColor, newColor);
      }
    }

  }

}
