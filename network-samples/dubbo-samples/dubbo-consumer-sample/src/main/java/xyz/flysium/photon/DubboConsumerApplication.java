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

package xyz.flysium.photon;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import xyz.flysium.photon.api.DemoService;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
@SpringBootApplication
@EnableDubbo
public class DubboConsumerApplication {

  @DubboReference
  private DemoService demoService;

  public static void main(String[] args) {
    new SpringApplicationBuilder().sources(DubboConsumerApplication.class).run(args);
  }

  @Bean
  public CommandLineRunner runner() {
    return args -> {
      System.out.println(demoService.sayHello("dubbo"));
      System.out.println(demoService.sayHello("Sven Augustus"));
      System.out.println(demoService.sayHello("zeno"));
    };
  }

}