package com.github.flysium.io.photon.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求对象
 *
 * @author Sven Augustus
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("PMD.UnusedPrivateField")
public class ApiRequest implements java.io.Serializable {

  private static final long serialVersionUID = -228920071542777101L;

  public static final String PARAM_SIGN = "sign";

  public static final String PARAM_SIGN_TYPE = "signType";

  public static final String SIGN_TYPE_HMAC_SHA256 = "HMAC-SHA256";

  public static final String SIGN_TYPE_SHA256 = "SHA256";

  public static final String SIGN_TYPE_MD5 = "MD5";

  private String appId;

  private String tenantId;

  private String transactionId;

  private String timestamp;

  private String nonceStr;

  private String sign;

  private String signType;

}
