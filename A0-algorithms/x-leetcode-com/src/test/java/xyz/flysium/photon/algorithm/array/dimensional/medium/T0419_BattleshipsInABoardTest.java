package xyz.flysium.photon.algorithm.array.dimensional.medium;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0419_BattleshipsInABoardTest {

  @Test
  public void test() {
    T0419_BattleshipsInABoard.Solution solution = new T0419_BattleshipsInABoard.Solution();

    Assert.assertEquals(2, solution
      .countBattleships(ArraySupport.toTwoDimensionalCharArray(
        "[[\"X\",\".\",\"X\"],[\"X\",\".\",\"X\"]]")));

    Assert.assertEquals(2, solution
      .countBattleships(ArraySupport.toTwoDimensionalCharArray(
        "[[\"X\",\".\",\".\",\"X\"],[\".\",\".\",\".\",\"X\"],[\".\",\".\",\".\",\"X\"]]")));

    Assert.assertEquals(1, solution
      .countBattleships(ArraySupport.toTwoDimensionalCharArray(
        "[[\".\",\".\"],[\"X\",\"X\"]]")));

    Assert.assertEquals(4, solution
      .countBattleships(ArraySupport.toTwoDimensionalCharArray(
        "[[\"X\",\".\",\"X\"],[\".\",\".\",\".\"],[\"X\",\".\",\"X\"]]")));
  }

  @Test
  public void test1() {
    T0419_BattleshipsInABoard_1.Solution solution = new T0419_BattleshipsInABoard_1.Solution();

    Assert.assertEquals(2, solution
      .countBattleships(ArraySupport.toTwoDimensionalCharArray(
        "[[\"X\",\".\",\"X\"],[\"X\",\".\",\"X\"]]")));

    Assert.assertEquals(2, solution
      .countBattleships(ArraySupport.toTwoDimensionalCharArray(
        "[[\"X\",\".\",\".\",\"X\"],[\".\",\".\",\".\",\"X\"],[\".\",\".\",\".\",\"X\"]]")));

    Assert.assertEquals(1, solution
      .countBattleships(ArraySupport.toTwoDimensionalCharArray(
        "[[\".\",\".\"],[\"X\",\"X\"]]")));

    Assert.assertEquals(4, solution
      .countBattleships(ArraySupport.toTwoDimensionalCharArray(
        "[[\"X\",\".\",\"X\"],[\".\",\".\",\".\"],[\"X\",\".\",\"X\"]]")));
  }

}
