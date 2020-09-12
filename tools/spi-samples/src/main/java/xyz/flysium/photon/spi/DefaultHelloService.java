package xyz.flysium.photon.spi;

/**
 * TODO description
 *
 * @author zeno
 */
public class DefaultHelloService implements HelloService {

  @Override
  public String hello(String message) {
    return message;
  }

}
