package com.github.flysium.io.photon.inoutput.c002_nio.s07_selector_group.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NIO Task
 *
 * @author Sven Augustus
 * @version 1.0
 */
public interface NIOTask {

  static final Logger logger = LoggerFactory.getLogger(NIOTask.class);

  /**
   * run
   */
  void run();
}
