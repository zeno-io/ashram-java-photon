package com.github.flysium.io.sample.disruptor;

import com.lmax.disruptor.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LongEvent ExceptionHandler.
 *
 * @author Sven Augustus
 */
public class EventExceptionHandler<T> implements ExceptionHandler<T> {

  private static final Logger LOGGER = LoggerFactory.getLogger(EventExceptionHandler.class);

  @Override
  public void handleEventException(Throwable ex, long sequence, T event) {
    LOGGER.error(Thread.currentThread().getName() + " | Exception : " + ex + " | Event : " + event);
  }

  @Override
  public void handleOnStartException(Throwable ex) {
    LOGGER.error(Thread.currentThread().getName() + " | StartException : " + ex);

  }

  @Override
  public void handleOnShutdownException(Throwable ex) {
    LOGGER.error(Thread.currentThread().getName() + " | ShutdownException : " + ex);
  }
}
