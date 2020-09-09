/*
 * MIT License
 *
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package xyz.flysium.photon;

import java.rmi.Naming;
import org.springframework.beans.factory.annotation.Value;
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
public class RmiConsumerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(RmiConsumerApplication.class).run(args);
    }

    @Value("${application.rmi.demoService.url}")
    private String demoServiceUrl;

    @Bean
    public CommandLineRunner runner() {
        return args -> {
            // 在 RMI 服务注册表中查找名称为 RDemoService 的对象，并调用其上的方法
            DemoService demoService = (DemoService) Naming.lookup(demoServiceUrl);
            System.out.println(demoService.sayHello("rmi"));
            System.out.println(demoService.sayHello("Sven Augustus"));
            System.out.println(demoService.sayHello("zeno"));
        };
    }

}
