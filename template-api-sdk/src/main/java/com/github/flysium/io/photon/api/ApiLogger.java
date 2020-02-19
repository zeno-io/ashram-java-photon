package com.github.flysium.io.photon.api;

import com.github.flysium.io.photon.api.dto.ApiLogVO;
import com.github.flysium.io.photon.api.logger.DefaultApiLogger;

/**
 * APIProperties 日志记录器
 *
 * @author Sven Augustus
 */
public interface ApiLogger {

  /**
   * APIProperties 日志记录
   *
   * @param log 日志对象
   */
  default void logger(ApiLogVO log) {
    logger(log, null);
  }

  /**
   * APIProperties 日志记录
   *
   * @param log 日志对象
   * @param response 响应对象
   */
  void logger(ApiLogVO log, Object response);

  /**
   * 获取API 日志记录器
   *
   * @return 日志记录器
   */
  static ApiLogger getApiLogger() {
    // TODO
    return new DefaultApiLogger();
  }

}
