package xyz.flysium.photon.concurrent.medium;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 1242. 多线程网页爬虫
 * <p>
 * https://leetcode-cn.com/problems/web-crawler-multithreaded
 *
 * @author zeno
 */
public class T1242_WebCrawlerMultithreadedV1 extends T1242_TestCaseForWebCrawlerMultithreaded {

  public static void main(String[] args) {
    Solution solution = new Solution();
    test1(solution::crawl);
    test2(solution::crawl);
    test3(solution::crawl);
    test4(solution::crawl);
  }

  /**
   * This is the HtmlParser's API interface.
   * <p>
   * You should not implement it, or speculate about its implementation
   * <pre>
   *   interface HtmlParser {
   *       public List<String> getUrls(String url) {}
   *   }
   * </pre>
   */
  private static class Solution {

    public List<String> crawl(String startUrl, HtmlParser htmlParser) {
      final Map<String, Integer> ans = new ConcurrentHashMap<>();
      final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

      ans.put(startUrl, 0);
      queue.offer(startUrl);

      int length = 7;//"http://".length();
      int endIndex = startUrl.indexOf("/", length);
      final String hostName = endIndex < 0 ? startUrl : startUrl.substring(0, endIndex);

      while (!queue.isEmpty()) {
        final int size = queue.size();
        final CountDownLatch latch = new CountDownLatch(size);

        for (int i = 0; i < size; i++) {
          String toGetUrl = queue.poll();
          Runnable r = () -> {
            List<String> urls = htmlParser.getUrls(toGetUrl);
            for (String url : urls) {
              if (ans.containsKey(url)) {
                continue;
              }
              if (url.startsWith(hostName)) {
                ans.put(url, 0);
                queue.offer(url);
              }
            }
            latch.countDown();
          };
          new Thread(r).start();
        }
        try {
          latch.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      return new ArrayList<>(ans.keySet());
    }
  }

}
