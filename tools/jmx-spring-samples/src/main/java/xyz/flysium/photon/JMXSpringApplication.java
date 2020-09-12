package xyz.flysium.photon;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.EnableMBeanExport;

/**
 * Boot
 *
 * @author zeno
 */
@SpringBootApplication
@EnableMBeanExport // 自动注册MBean
public class JMXSpringApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(JMXSpringApplication.class).run(args);
  }

}
