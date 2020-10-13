package xyz.flysium.photon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * 质数
 *
 * @author zeno
 */
public final class PrimeSupport {

  private PrimeSupport() {
  }

  public static void main(String[] args) {
    System.out.println(Arrays.toString(printPrime(100)));
    System.out.println(Arrays.equals(printPrime(2, 100, PrimeSupport::isPrimeClassic),
      printPrime(2, 100, PrimeSupport::isPrimeSqrt)));
    System.out.println(Arrays.equals(printPrime(2, 100, PrimeSupport::isPrimeClassic),
      printPrime(2, 100, PrimeSupport::isPrimeSix)));
  }


  /**
   * 打印质数
   *
   * @param N 打印几个质数
   */
  public static Integer[] printPrime(int N) {
    return printPrime(2, N);
  }

  /**
   * 打印质数
   *
   * @param start 从那个非负数开始
   * @param N     打印几个质数
   */
  public static Integer[] printPrime(int start, int N) {
    return printPrime(start, N, PrimeSupport::isPrime);
  }

  private static Integer[] printPrime(int start, int N, Function<Integer, Boolean> isPrime) {
    return findPrimes(start, N, isPrime);
  }

  private static Integer[] findPrimes(int start, int n, Function<Integer, Boolean> isPrime) {
    if (start <= 1) {
      start = 2;
    }
    List<Integer> ans = new ArrayList<>(n);
    int i = 0;
    int num = start;
    while (i < n) {
      if (isPrime.apply(num)) {
        ans.add(num);
        i++;
      }
      num++;
    }
    return ans.toArray(new Integer[0]);
  }

  public static boolean isPrime(int num) {
    return isPrimeSix(num);
  }

  // 质数还有一个特点，就是它总是等于 6x-1 或者 6x+1，其中 x 是大于等于1的自然数。
  //
  //  论证:
  //    首先 6x 肯定不是质数，因为它能被 6 整除；
  //    其次 6x+2 肯定也不是质数，因为它还能被2整除；
  //    依次类推，6x+3 肯定能被 3 整除；
  //    6x+4 肯定能被 2 整除。
  //    那么，就只有 6x+1 和 6x+5 (即等同于6x-1) 可能是质数了。
  //    所以循环的步长可以设为 6，然后每次只判断 6 两侧的数即可。
  private static Boolean isPrimeSix(Integer num) {
    if (num <= 3) {
      return num > 1;
    }
    // 不在6的倍数两侧的一定不是质数
    if (num % 6 != 1 && num % 6 != 5) {
      return false;
    }
    final int sqrt = (int) Math.sqrt(num);
    for (int i = 5; i <= sqrt; i += 6) {
      if (num % i == 0 || num % (i + 2) == 0) {
        return false;
      }
    }
    return true;
  }

  // O(N * sqrt(N))
  // 给定一个正整数n，用2到sqrt(n)之间的所有整数去除n，如果可以整除，则n不是素数，如果不可以整除，则n就是素数。
  // 定理: 如果n不是素数, 则n有满足1< d<=sqrt(n)的一个因子d.
  // 证明: 如果n不是素数, 则根据定义n必有一个因子d满足1< d< n。如果d大于等于sqrt(n), 则n/d是满足1< n/d<=sqrt(n)的另一个因子。
  private static boolean isPrimeSqrt(int num) {
    if (num <= 3) {
      return num > 1;
    }
    final int sqrt = (int) Math.sqrt(num);
    for (int k = 2; k <= sqrt; k++) {
      if (num % k == 0) {
        return false;
      }
    }
    return true;
  }

  // O(N ^ 2)
  private static boolean isPrimeClassic(int num) {
    if (num <= 3) {
      return num > 1;
    }

    for (int k = 2; k < num - 1; k++) {
      if (num % k == 0) {
        return false;
      }
    }
    return true;
  }


}
