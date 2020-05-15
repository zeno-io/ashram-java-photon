package com.github.flysium.io.photon.api.service;

import com.github.flysium.io.photon.api.dto.ApiConfiguration;
import com.github.flysium.io.photon.api.dto.AppSecret;
import lombok.AllArgsConstructor;

/**
 * APIProperties 查询器
 *
 * @author Sven Augustus
 */
@AllArgsConstructor
public class ApiQueryServiceImpl implements ApiQueryService {

  @Override
  public AppSecret queryAppSecretByAppId(String appId) {
    // TODO
    return AppSecret.builder().appId(appId).signatureCheckEnabled(true).appKey("xxxx").build();
  }

  @Override
  public ApiConfiguration queryApiConfigurationByAppId(String appId, String apiId) {
    // TODO
    return ApiConfiguration.builder().apiId(apiId).url("http://localhost:8090/mock").build();
  }
}
