package xyz.flysium.photon.concurrent.medium;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 1242. 多线程网页爬虫
 * <p>
 * https://leetcode-cn.com/problems/web-crawler-multithreaded
 *
 * @author zeno
 */
public class T1242_WebCrawlerMultithreaded extends T1242_TestCaseForWebCrawlerMultithreaded {

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

      final int core = Math.max(Runtime.getRuntime().availableProcessors() - 1, 4);
      final Thread[] ts = new Thread[core];
      final AtomicBoolean[] tf = new AtomicBoolean[core];
      for (int i = 0; i < tf.length; i++) {
        tf[i] = new AtomicBoolean();
      }
      for (int i = 0; i < ts.length; i++) {
        final int index = i;
        Runnable r = () -> {
          boolean end = false;
          while (!end) {
            while (!queue.isEmpty()) {
              String toGetUrl = queue.poll();
              tf[index].compareAndSet(false, true);
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
              tf[index].compareAndSet(true, false);
            }
            end = queue.isEmpty();
            if (end) {
              for (int k = 0; k < ts.length; k++) {
                if (index != k && tf[k].get()) {
                  end = false;
                  break;
                }
              }
              if (end) {
                for (int k = 0; k < ts.length; k++) {
                  if (index != k) {
                    ts[k].interrupt();
                  }
                }
              }
            }
          }
        };
        ts[index] = new Thread(r);
      }
      for (Thread t : ts) {
        t.start();
      }
      for (Thread t : ts) {
        try {
          t.join();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      return new ArrayList<>(ans.keySet());
    }
  }

}
