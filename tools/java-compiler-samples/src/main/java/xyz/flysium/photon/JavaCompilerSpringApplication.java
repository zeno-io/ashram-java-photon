package xyz.flysium.photon;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import xyz.flysium.photon.util.ClassPathUtils;
import xyz.flysium.photon.util.JavaCompilerUtils;
import xyz.flysium.photon.util.JavaCompilerUtils.JavaSourceFromString;

/**
 * Boot
 *
 * @author zeno
 */
@SpringBootApplication
public class JavaCompilerSpringApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(JavaCompilerSpringApplication.class).run(args);
  }

  @Bean
  public CommandLineRunner commandLineRunner() {
    return args -> {
      String classPathForSpringBoot = ClassPathUtils.getClassPathForSpringBoot();
      List<Diagnostic<? extends JavaFileObject>> diagnostics = JavaCompilerUtils
          .compileJavaSources(Collections.singletonList(
              new JavaSourceFromString(
                  "A",
                  "package xyz.flysium.photon;\n"
                      + "\n"
                      + "import org.springframework.stereotype.Component;\n"
                      + "\n"
                      + "/**\n"
                      + " * TODO description\n"
                      + " *\n"
                      + " * @author zeno\n"
                      + " */\n"
                      + "@Component\n"
                      + "public class A {\n"
                      + "\n"
                      + "  public void m() {\n"
                      + "    System.out.println(\"Hello !\"+B.class);\n"
                      + "  }\n"
                      + "}")
          ), Arrays.asList("-encoding", "UTF-8", "-source", "1.8", "-cp", classPathForSpringBoot));
      System.out.println(diagnostics);
    };
  }
}
