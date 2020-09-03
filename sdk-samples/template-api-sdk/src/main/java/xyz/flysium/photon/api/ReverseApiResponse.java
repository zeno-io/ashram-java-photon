/*
 * MIT License
 *
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package xyz.flysium.photon.api;

/**
 * 通知返回对象
 *
 * @author Sven Augustus
 */
@SuppressWarnings("PMD.UnusedPrivateField")
public class ReverseApiResponse implements java.io.Serializable {

  private static final long serialVersionUID = 4655297366604866598L;

  private String returnCode;

  private String returnMsg;

  public String getReturnCode() {
    return returnCode;
  }

  public void setReturnCode(String returnCode) {
    this.returnCode = returnCode;
  }

  public String getReturnMsg() {
    return returnMsg;
  }

  public void setReturnMsg(String returnMsg) {
    this.returnMsg = returnMsg;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private final ReverseApiResponse reverseApiResponse;

    private Builder() {
      reverseApiResponse = new ReverseApiResponse();
    }

    public Builder returnCode(String returnCode) {
      reverseApiResponse.setReturnCode(returnCode);
      return this;
    }

    public Builder returnMsg(String returnMsg) {
      reverseApiResponse.setReturnMsg(returnMsg);
      return this;
    }

    public ReverseApiResponse build() {
      return reverseApiResponse;
    }
  }
}
