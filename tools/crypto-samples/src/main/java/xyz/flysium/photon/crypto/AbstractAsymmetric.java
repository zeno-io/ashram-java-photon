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

package xyz.flysium.photon.crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import xyz.flysium.photon.crypto.support.CryptoUtil;

/**
 * Asymmetric encryption and decryption algorithm
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.7
 */
public abstract class AbstractAsymmetric extends AbstractAsyCryptoSpi implements AsyCryptoSpi {

  /**
   * the public key
   */
  protected PublicKey publicKey;
  /**
   * the private key
   */
  protected PrivateKey privateKey;
  /**
   * the signature algorithm name
   */
  private String signatureAlgorithm;

  private Signature getSignature() {
    return CryptoUtil
      .getSignature(AbstractAsymmetric.this.signatureAlgorithm,
        AbstractAsymmetric.this.provider);
  }

  public AbstractAsymmetric(String algorithm, Provider provider, String transforms) {
    super(algorithm, provider, transforms);
  }

  public AbstractAsymmetric(String algorithm, Provider provider, String transforms,
    String publicKeyB64,
    String privateKeyB64) {
    super(algorithm, provider, transforms);
    this.setPublicKey(publicKeyB64);
    this.setPrivateKey(privateKeyB64);
  }

  @Override
  public Provider getProvider() {
    return provider;
  }

  @Override
  public String getTransforms() {
    return transforms;
  }

  @Override
  public String getSignatureAlgorithm() {
    return signatureAlgorithm;
  }

  @Override
  public void setSignatureAlgorithm(String signatureAlgorithm) {
    this.signatureAlgorithm = signatureAlgorithm;
  }

  public KeyPair getKeyPair() {
    return new KeyPair(publicKey, privateKey);
  }

  public void setKeyPair(KeyPair keyPair) {
    this.publicKey = keyPair.getPublic();
    this.privateKey = keyPair.getPrivate();
  }

  @Override
  public byte[] getSecret() {
    throw new UnsupportedOperationException("not support to get secret in Asymmetric.");
  }

  @Override
  public void setSecret(byte[] secretKey) {
    throw new UnsupportedOperationException("not support to set secret in Asymmetric.");
  }

  @Override
  public String getPublicKey() {
    return new String(CryptoUtil.armor(this.publicKey.getEncoded()), CryptoUtil.DEFAULT_CHARSET);
  }

  @Override
  public String getPrivateKey() {
    return new String(CryptoUtil.armor(this.privateKey.getEncoded()), CryptoUtil.DEFAULT_CHARSET);
  }

  @Override
  public void generateKey(int keyLength) {
    KeyPair keyPair = CryptoUtil.generateKeyPair(keyLength, algorithm, provider);
    this.publicKey = keyPair.getPublic();
    this.privateKey = keyPair.getPrivate();
  }

  @Override
  public byte[] encrypt(byte[] plainText) {
    Cipher cipher = getCipher();
    try {
      if (cipher != null) {
        if (algorithmParameterSpec != null) {
          cipher.init(Cipher.ENCRYPT_MODE, this.publicKey, algorithmParameterSpec);
        } else {
          cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
        }

        return doFinal(cipher, Cipher.ENCRYPT_MODE, plainText);
      }
    } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
      fail(e);
    }
    return new byte[0];
  }

  @Override
  public byte[] decrypt(byte[] cipherText) {
    Cipher cipher = getCipher();
    try {
      if (cipher != null) {
        if (algorithmParameterSpec != null) {
          cipher.init(Cipher.DECRYPT_MODE, this.privateKey, algorithmParameterSpec);
        } else {
          cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
        }

        return doFinal(cipher, Cipher.DECRYPT_MODE, cipherText);
      }
    } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
      fail(e);
    }
    return new byte[0];
  }

  protected byte[] doFinal(Cipher cipher, int mode, byte[] input) {
    try {
      return cipher.doFinal(input);
    } catch (IllegalBlockSizeException | BadPaddingException e) {
      fail(e);
    }
    return new byte[0];
  }

  @Override
  public byte[] sign(byte[] input) {
    Signature signature = getSignature();
    try {
      if (signature != null) {
        signature.initSign(privateKey);
        signature.update(input);

        return signature.sign();
      }
    } catch (InvalidKeyException | SignatureException e) {
      fail(e);
    }
    return new byte[0];
  }

  @Override
  public boolean verify(byte[] input, byte[] sign) {
    Signature signature = getSignature();
    try {
      if (signature != null) {
        signature.initVerify(this.publicKey);
        signature.update(input);

        return signature.verify(sign);
      }
    } catch (InvalidKeyException | SignatureException e) {
      fail(e);
    }
    return false;
  }

}
