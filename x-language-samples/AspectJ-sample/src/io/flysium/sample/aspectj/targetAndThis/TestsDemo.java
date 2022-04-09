package io.flysium.sample.aspectj.targetAndThis;

import java.util.Arrays;
import java.util.List;

public class TestsDemo {

  public static void main(String[] args) {
    new TestsDemo().move(Arrays.asList(new Dragon(), new Tiger()));
  }

  void move(List<Animal> list) {
    for (Animal a : list) {
      a.move();
    }
  }

}
