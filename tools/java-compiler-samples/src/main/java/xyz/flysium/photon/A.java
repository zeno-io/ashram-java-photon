package xyz.flysium.photon;

import org.springframework.stereotype.Component;

/**
 * TODO description
 *
 * @author zeno
 */
@Component
public class A {

  public void m() {
    System.out.println("Hello !"+B.class);
  }
}
