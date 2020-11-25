/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.flysium.photon.crypto.support;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * 加密参数规范工具类
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.7
 */
public final class SpecUtil extends CryptoUtil {

  private SpecUtil() {
  }

  /**
   * 默认迭代次数
   */
  public static final int DEFAULT_ITERATIONS = 1000;

  /**
   * 构建加密参数规范：初始化向量 (IV)
   *
   * @param iv 向量 (IV)
   * @return 加密参数规范
   */
  @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("STATIC_IV")
  public static IvParameterSpec buildIV(byte[] iv) {
    return new IvParameterSpec(iv);
  }

  /**
   * 构建加密参数规范：以密码为基础的加密法 (PBE) 使用的参数集合
   *
   * @param salt 盐
   * @return 加密参数规范
   */
  public static PBEParameterSpec buildPBE(byte[] salt) {
    return buildPBE(salt, DEFAULT_ITERATIONS);
  }

  /**
   * 构建加密参数规范：以密码为基础的加密法 (PBE) 使用的参数集合
   *
   * @param salt           盐
   * @param iterationCount 迭代次数
   * @return 加密参数规范
   */
  public static PBEParameterSpec buildPBE(byte[] salt, int iterationCount) {
    return new PBEParameterSpec(salt, iterationCount);
  }

  /**
   * 构建加密参数规范：以密码为基础的加密法 (PBE) 使用的参数集合
   *
   * @param salt 盐
   * @return 加密参数规范
   */
  public static PBEParameterSpec buildPBE(String salt) {
    return buildPBE(salt.getBytes(DEFAULT_CHARSET), DEFAULT_ITERATIONS);
  }


  /**
   * 构建加密参数规范：以密码为基础的加密法 (PBE) 使用的参数集合
   *
   * @param saltHex 盐
   * @return 加密参数规范
   */
  public static PBEParameterSpec buildPBEHex(String saltHex) {
    return buildPBEHex(saltHex, DEFAULT_ITERATIONS);
  }

  /**
   * 构建加密参数规范：以密码为基础的加密法 (PBE) 使用的参数集合
   *
   * @param saltHex        盐
   * @param iterationCount 迭代次数
   * @return 加密参数规范
   */
  public static PBEParameterSpec buildPBEHex(String saltHex, int iterationCount) {
    return buildPBE(unhex(saltHex), iterationCount);
  }

}
