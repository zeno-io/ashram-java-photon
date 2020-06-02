/*
 * Copyright 2020 SvenAugustus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.netty.serializer;

/**
 * Serializer Interface.
 *
 * @author Sven Augustus
 * @version 1.0
 */
public interface Serializer {

  /**
   * Get The name of Serializer
   *
   * @return The name of Serializer
   */
  String name();

  /**
   * serialize object to byte array.
   *
   * @param object Object
   * @return data byte array
   * @throws Exception any exception while in serialize
   */
  <T> byte[] serialize(T object) throws Exception;

  /**
   * deserialize byte array  to object.
   *
   * @param data data byte array
   * @param type Type of data
   * @return object
   * @throws Exception any exception while in deserialize
   */
  <T> T deserialize(byte[] data, Class<T> type) throws Exception;

}
