package com.github.flysium.io.photon.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应对象
 *
 * @author Sven Augustus
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("PMD.UnusedPrivateField")
public final class ApiResponse implements java.io.Serializable {

  private static final long serialVersionUID = -5278688902213849773L;

  private String returnCode;

  private String returnMsg;

  private String appId;

  private String tenantId;

  /* 接口日志流水ID */
  private String apiTransId;

  private String timestamp;

  private String nonceStr;

  private String sign;

  private String signType;

  private String resultCode;

  private String errCode;

  private String errCodeDes;

  public static ApiResponse success() {
    return success(ApiResponse.builder().build());
  }

  public static ApiResponse success(ApiResponse response) {
    response.setReturnCode(ApiInvoker.SUCCESS);
    response.setReturnMsg("处理成功");
    return response;
  }

}
