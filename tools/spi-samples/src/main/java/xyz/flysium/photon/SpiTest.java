package xyz.flysium.photon;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;
import xyz.flysium.photon.spi.DefaultHelloService;
import xyz.flysium.photon.spi.HelloService;

/**
 * TODO description
 *
 * @author zeno
 */
public class SpiTest {

  public static void main(String[] args) {
    HelloService service = getHelloSpi();
    System.out.println(service.hello("zeno"));
  }

  private static HelloService getHelloSpi() {
    ServiceLoader<HelloService> loader = ServiceLoader.load(HelloService.class,
        Thread.currentThread().getContextClassLoader());
    final Optional<HelloService> serializer = StreamSupport
        .stream(loader.spliterator(), false)
        .findFirst();
    return serializer.orElse(new DefaultHelloService());
  }

}
