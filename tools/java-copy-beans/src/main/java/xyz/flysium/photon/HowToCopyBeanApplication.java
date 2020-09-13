package xyz.flysium.photon;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Boot
 *
 * @author zeno
 */
@SpringBootApplication
public class HowToCopyBeanApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(HowToCopyBeanApplication.class).run(args);
  }

}
