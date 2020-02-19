package com.github.flysium.io.photon.api.logger;

import com.github.flysium.io.photon.api.ApiLogger;
import com.github.flysium.io.photon.api.dto.ApiLogVO;
import lombok.AllArgsConstructor;

/**
 * APIProperties 日志记录
 *
 * @author Sven Augustus
 */
@AllArgsConstructor
public class DefaultApiLogger implements ApiLogger {

  @Override
  public void logger(ApiLogVO log, Object response) {
    // TODO 异步写日志
  }

}
