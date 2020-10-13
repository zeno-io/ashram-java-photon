package xyz.flysium.photon.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

/**
 * 1242. 多线程网页爬虫 测试用例
 *
 * @author zeno
 */
public abstract class T1242_TestCaseForWebCrawlerMultithreaded {

  protected static void test1(BiFunction<String, HtmlParser, List> crawl) {
    String[] urls = new String[]{
      "http://news.yahoo.com",
      "http://news.yahoo.com/news",
      "http://news.yahoo.com/news/topics/",
      "http://news.google.com",
      "http://news.yahoo.com/us"
    };
    int[][] edges = new int[][]{
      new int[]{2, 0}, new int[]{2, 1}, new int[]{3, 2}, new int[]{3, 1}, new int[]{0, 4}
    };
    String startUrl = "http://news.yahoo.com/news/topics/";
    long start = System.currentTimeMillis();
    System.out.println(crawl.apply(startUrl, new MockHtmlParser(urls, edges)));
    System.out.println((System.currentTimeMillis() - start) + "ms");
  }

  protected static void test2(BiFunction<String, HtmlParser, List> crawl) {
    String[] urls = new String[]{
      "http://news.yahoo.com",
      "http://news.yahoo.com/news",
      "http://news.yahoo.com/news/topics/",
      "http://news.google.com"
    };
    int[][] edges = new int[][]{
      new int[]{0, 2}, new int[]{2, 1}, new int[]{3, 2}, new int[]{3, 1}, new int[]{3, 0}
    };
    String startUrl = "http://news.google.com";
    long start = System.currentTimeMillis();
    System.out.println(crawl.apply(startUrl, new MockHtmlParser(urls, edges)));
    System.out.println((System.currentTimeMillis() - start) + "ms");
  }

  protected static void test3(BiFunction<String, HtmlParser, List> crawl) {
    String[] urls = new String[]{
      "http://psn.wlyby.edu/wvoz", "http://psn.wlyby.edu/shez", "http://psn.wlyby.edu/upkr",
      "http://psn.wlyby.edu/ubmr", "http://psn.wlyby.edu/apgb", "http://psn.wlyby.edu/sbin",
      "http://psn.wlyby.edu/inmj", "http://cpq.jkvox.tech/mjkb", "http://lqr.shmtu.tech/rsvw",
      "http://ylk.fubmn.com/ypyh"
    };
    int[][] edges = new int[][]{
      new int[]{0, 8}, new int[]{1, 6}, new int[]{1, 7}, new int[]{1, 4}, new int[]{3, 3},
      new int[]{3, 4}, new int[]{3, 7}, new int[]{4, 1}, new int[]{4, 0}, new int[]{4, 3},
      new int[]{5, 5}, new int[]{5, 8}, new int[]{5, 5}, new int[]{5, 0}, new int[]{
      6, 8}, new int[]{7, 2}, new int[]{7, 7}, new int[]{7, 4}, new int[]{10, 7}, new int[]{10, 4},
      new int[]{10, 3}, new int[]{10, 4}
    };
    String startUrl = "http://psn.wlyby.edu/ubmr";
    long start = System.currentTimeMillis();
    System.out.println(crawl.apply(startUrl, new MockHtmlParser(urls, edges)));
    System.out.println((System.currentTimeMillis() - start) + "ms");
  }

  protected static void test4(BiFunction<String, HtmlParser, List> crawl) {
    String[] urls = new String[1000];
    urls[0] = "http://sta.zizqt.xyz/vqra";
    final ThreadLocalRandom random = ThreadLocalRandom.current();
    final char[] chars = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
    for (int i = 1; i < urls.length; i++) {
      urls[i] = "http://sta.zizqt.xyz/";
      for (int j = 0; j < 4; j++) {
        urls[i] += chars[random.nextInt(chars.length)];
      }
    }
    List<int[]> l = new LinkedList<>();
    for (int i = 0; i < urls.length; i++) {
      for (int j = 0; j < urls.length; j++) {
        if (i != j) {
          l.add(new int[]{i, j});
        }
      }
    }
    int[][] edges = new int[l.size()][2];
    l.toArray(edges);
    String startUrl = "http://sta.zizqt.xyz/vqra";
    long start = System.currentTimeMillis();
    System.out.println(crawl.apply(startUrl, new MockHtmlParser(urls, edges)));
    System.out.println((System.currentTimeMillis() - start) + "ms");
  }

  public static class MockHtmlParser implements HtmlParser {

    private final Map<String, List<String>> map = new HashMap<>();

    public MockHtmlParser(String[] urls, int[][] map) {
      for (int[] edge : map) {
        int start = edge[0];
        int end = edge[1];
        if (start >= 0 && start < urls.length && end >= 0 && end < urls.length) {
          this.map.putIfAbsent(urls[start], new ArrayList<>());
          List<String> list = this.map.get(urls[start]);
          if (!list.contains(urls[end])) {
            list.add(urls[end]);
          }
        }
      }
    }

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    @Override
    public List<String> getUrls(String url) {
      try {
//        int timeout = random.nextInt(16);
        int timeout = 15;
        TimeUnit.MILLISECONDS.sleep(timeout);
      } catch (InterruptedException e) {
      }
      List<String> list = map.get(url);
      if (list == null) {
        return Collections.emptyList();
      }
      return new ArrayList<>(list);
    }
  }

  public static interface HtmlParser {

    List<String> getUrls(String url);

  }

}
