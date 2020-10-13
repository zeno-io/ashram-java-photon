package xyz.flysium.photon.algorithm.binary.medium;

import java.util.Arrays;

/**
 * 在一个排好序的数组里，找出某个数字最后一次出现的位置？
 * <p>
 * 34. 在排序数组中查找元素的第一个和最后一个位置
 * <p>
 * https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array/
 *
 * @author zeno
 */
public class T0034_FindFirstAndLastPositionOfElementInSortedArray {

  public static void main(String[] args) {
    //Scanner in = new Scanner(System.in);
    //int a = in.nextInt();
    //System.out.println(a);
    int[] arr = new int[]{1, 2, 3, 3, 4, 5, 6, 6, 7, 8, 9};
    Solution solution = new Solution();
    System.out.println(Arrays.toString(solution.searchRange(arr, 0, arr.length - 1, 3)));
    System.out.println(Arrays.toString(solution.searchRange(arr, 0, arr.length - 1, 6)));
    System.out.println(Arrays.toString(solution.searchRange(arr, 0, arr.length - 1, 10)));
    System.out.println(Arrays.toString(solution.searchRange(arr, 0, arr.length - 1, 0)));
  }

  static class Solution {

    public int[] searchRange(int[] nums, int target) {
      return searchRange(nums, 0, nums.length - 1, target);
    }

    private int[] searchRange(int[] arr, int l, int r, int target) {
      if (l >= r) {
        if (l >= arr.length || arr[l] != target) {
          return new int[]{-1, -1};
        }
        return startToEnd(arr, l, target);
      }
      int mid = l + ((r - l + 1) >> 1);
      if (arr[mid] == target) {
        return startToEnd(arr, mid, target);
      }
      if (arr[mid] > target) {
        return searchRange(arr, l, mid - 1, target);
      }
      return searchRange(arr, mid + 1, r, target);
    }

    private int[] startToEnd(int[] arr, int index, int target) {
      int start = index;
      int end = index;
      while (start >= 0 && arr[start] == target) {
        start--;
      }
      while (end < arr.length && arr[end] == target) {
        end++;
      }
      return new int[]{start + 1, end - 1};
    }
  }

}
