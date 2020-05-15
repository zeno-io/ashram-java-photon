package com.github.flysium.io.photon.api;

/**
 * API业务调用
 *
 * @author Sven Augustus
 */
public interface ApiInvoker<I extends ApiRequest, O extends ExtendApiResponse> {

  /**
   * API业务调用
   *
   * @param request 请求对象
   * @return 扩展响应对象
   */
  O invoke(I request);

  /* 成功 **/
  public static final String SUCCESS = "SUCCESS";

  /* 失败 **/
  public static final String FAIL = "FAIL";
}
