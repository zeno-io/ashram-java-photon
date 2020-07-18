package com.github.flysium.io.photon.inoutput.c002_nio.s07_selector_group;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Net Util
 *
 * @author Sven Augustus
 * @version 1.0
 */
public final class NetUtil {

  static final Logger logger = LoggerFactory.getLogger(NetUtil.class);

  private NetUtil() {
  }

  /**
   * @see io.netty.util.NetUtil
   */
  public static int getSomaxconn() {
    // As a SecurityManager may prevent reading the somaxconn file we wrap this in a privileged block.
    //
    // See https://github.com/netty/netty/issues/3680
    return AccessController.doPrivileged(new PrivilegedAction<Integer>() {
      @Override
      public Integer run() {
        // Determine the default somaxconn (server socket backlog) value of the platform.
        // The known defaults:
        // - Windows NT Server 4.0+: 200
        // - Linux and Mac OS X: 128
        int somaxconn = isWindows0() ? 200 : 128;
        File file = new File("/proc/sys/net/core/somaxconn");
        BufferedReader in = null;
        try {
          // file.exists() may throw a SecurityException if a SecurityManager is used, so execute it in the
          // try / catch block.
          // See https://github.com/netty/netty/issues/4936
          if (file.exists()) {
            in = new BufferedReader(new FileReader(file));
            somaxconn = Integer.parseInt(in.readLine());
            if (logger.isDebugEnabled()) {
              logger.debug("{}: {}", file, somaxconn);
            }
          } else {
            // Try to get from sysctl
            Integer tmp = null;
//            if (SystemPropertyUtil.getBoolean("io.netty.net.somaxconn.trySysctl", false)) {
            tmp = sysctlGetInt("kern.ipc.somaxconn");
            if (tmp == null) {
              tmp = sysctlGetInt("kern.ipc.soacceptqueue");
              if (tmp != null) {
                somaxconn = tmp;
              }
            } else {
              somaxconn = tmp;
            }
//            }

            if (tmp == null) {
              logger.debug("Failed to get SOMAXCONN from sysctl and file {}. Default: {}", file,
                  somaxconn);
            }
          }
        } catch (Exception e) {
          if (logger.isDebugEnabled()) {
            logger.debug("Failed to get SOMAXCONN from sysctl and file {}. Default: {}",
                file, somaxconn, e);
          }
        } finally {
          if (in != null) {
            try {
              in.close();
            } catch (Exception e) {
              // Ignored.
            }
          }
        }
        return somaxconn;
      }
    });
  }

  private static boolean isWindows0() {
    boolean windows = SystemPropertyUtilGet("os.name", "").toLowerCase(Locale.US).contains("win");
    if (windows) {
      logger.debug("Platform: Windows");
    }
    return windows;
  }

  /**
   * Returns the value of the Java system property with the specified {@code key}, while falling
   * back to the specified default value if the property access fails.
   *
   * @return the property value. {@code def} if there's no such property or if an access to the
   * specified property is not allowed.
   */
  public static String SystemPropertyUtilGet(final String key, String def) {
//    ObjectUtil.checkNotNull(key, "key");
    if (key.isEmpty()) {
      throw new IllegalArgumentException("key must not be empty.");
    }

    String value = null;
    try {
      if (System.getSecurityManager() == null) {
        value = System.getProperty(key);
      } else {
        value = AccessController.doPrivileged(new PrivilegedAction<String>() {
          @Override
          public String run() {
            return System.getProperty(key);
          }
        });
      }
    } catch (SecurityException e) {
      logger
          .warn("Unable to retrieve a system property '{}'; default values will be used.", key, e);
    }

    if (value == null) {
      return def;
    }

    return value;
  }

  /**
   * This will execute <a href ="https://www.freebsd.org/cgi/man.cgi?sysctl(8)">sysctl</a> with the
   * {@code sysctlKey} which is expected to return the numeric value for for {@code sysctlKey}.
   *
   * @param sysctlKey The key which the return value corresponds to.
   * @return The <a href ="https://www.freebsd.org/cgi/man.cgi?sysctl(8)">sysctl</a> value for
   * {@code sysctlKey}.
   */
  private static Integer sysctlGetInt(String sysctlKey) throws IOException {
    Process process = new ProcessBuilder("sysctl", sysctlKey).start();
    try {
      InputStream is = process.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      try {
        String line = br.readLine();
        if (line != null && line.startsWith(sysctlKey)) {
          for (int i = line.length() - 1; i > sysctlKey.length(); --i) {
            if (!Character.isDigit(line.charAt(i))) {
              return Integer.valueOf(line.substring(i + 1));
            }
          }
        }
        return null;
      } finally {
        br.close();
      }
    } finally {
      if (process != null) {
        process.destroy();
      }
    }
  }

}
