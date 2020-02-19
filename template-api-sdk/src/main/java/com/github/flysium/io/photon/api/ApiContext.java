package com.github.flysium.io.photon.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * APIProperties 协议相关上下文
 *
 * @author Sven Augustus
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("PMD.UnusedPrivateField")
public class ApiContext {

  private transient String protocolType;

  private transient String clientIp;

  private transient String serverIp;

  private String url;

  private static ThreadLocal<ApiContext> contextThreadLocal = new ThreadLocal<ApiContext>() {
    @Override
    protected ApiContext initialValue() {
      return new ApiContext();
    }
  };

  public static ApiContext get() {
    return contextThreadLocal.get();
  }

  public static void set(ApiContext value) {
    contextThreadLocal.set(value);
  }

  public static void remove() {
    contextThreadLocal.remove();
  }
}
