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

package com.github.flysium.io.photon.lock;

/**
 * Interrupted Exception for Lock
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class LockInterruptedException extends RuntimeException {

  private static final long serialVersionUID = -4547290446406543909L;

  public LockInterruptedException() {
    super();
  }

  public LockInterruptedException(String message) {
    super(message);
  }

  public LockInterruptedException(String message, Throwable cause) {
    super(message, cause);
  }

  public LockInterruptedException(Throwable cause) {
    super(cause);
  }

  protected LockInterruptedException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
