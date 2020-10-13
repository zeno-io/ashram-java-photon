package xyz.flysium.photon.algorithm.array.move.easy;

import java.util.Arrays;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0283_MoveZeroesTest {

  @Test
  public void test() {
    T0283_MoveZeroes.Solution solution = new T0283_MoveZeroes.Solution();
    int[] nums = ArraySupport.newArray("[1, 2, 0, 4, 7, 0, 9, 12, 0, 0, 6, 0]");
    solution.moveZeroes(nums);
    System.out.println(Arrays.toString(nums));
    nums = ArraySupport.newArray("[0, 2, 0, 4, 7, 0, 9, 12, 0, 0, 6, 0]");
    solution.moveZeroes(nums);
    System.out.println(Arrays.toString(nums));
  }

  @Test
  public void test1() {
    T0283_MoveZeroes_1.Solution solution = new T0283_MoveZeroes_1.Solution();
    int[] nums = ArraySupport.newArray("[1, 2, 0, 4, 7, 0, 9, 12, 0, 0, 6, 0]");
    solution.moveZeroes(nums);
    System.out.println(Arrays.toString(nums));
    nums = ArraySupport.newArray("[0, 2, 0, 4, 7, 0, 9, 12, 0, 0, 6, 0]");
    solution.moveZeroes(nums);
    System.out.println(Arrays.toString(nums));
  }

}
