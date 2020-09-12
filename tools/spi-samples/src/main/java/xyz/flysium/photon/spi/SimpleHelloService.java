package xyz.flysium.photon.spi;

/**
 * TODO description
 *
 * @author zeno
 */
public class SimpleHelloService implements HelloService {

  @Override
  public String hello(String message) {
    return "hello, " + message;
  }

}
