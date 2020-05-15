package com.github.flysium.io.photon.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通知返回对象
 *
 * @author Sven Augustus
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("PMD.UnusedPrivateField")
public class ReverseApiResponse implements java.io.Serializable {

  private static final long serialVersionUID = 4655297366604866598L;

  private String returnCode;

  private String returnMsg;

}
