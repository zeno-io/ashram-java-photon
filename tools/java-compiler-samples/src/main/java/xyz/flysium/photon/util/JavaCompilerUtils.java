package xyz.flysium.photon.util;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * Utils for <code>JavaCompiler</code>
 *
 * @author zeno
 */
public final class JavaCompilerUtils {

  private JavaCompilerUtils() {
  }

  /**
   * compile java source files
   *
   * @param sourceFileList java source files
   * @return empty if compile success, otherwise error diagnostics
   * @throws IOException any compile exception
   */
  public static List<Diagnostic<? extends JavaFileObject>> compileJavaFileList(
      List<File> sourceFileList)
      throws IOException {
    return compileJavaFileList(sourceFileList,
        Arrays.asList("-encoding", "UTF-8", "-source", "1.8"));
  }

  /**
   * compile java source files
   *
   * @param sourceFileList       java source files
   * @param targetClassDirectory class output directory
   * @return empty if compile success, otherwise error diagnostics
   * @throws IOException any compile exception
   */
  public static List<Diagnostic<? extends JavaFileObject>> compileJavaFileList(
      List<File> sourceFileList,
      File targetClassDirectory)
      throws IOException {
    return compileJavaFileList(sourceFileList, Arrays.asList("-encoding", "UTF-8",
        "-d", targetClassDirectory.getAbsolutePath()));
  }

  /**
   * compile java source files
   *
   * @param sourceFileList java source files
   * @param options        options for javac
   * @return empty if compile success, otherwise error diagnostics
   * @throws IOException any compile exception
   */
  public static List<Diagnostic<? extends JavaFileObject>> compileJavaFileList(
      List<File> sourceFileList, Iterable<String> options)
      throws IOException {
    if (sourceFileList == null || sourceFileList.isEmpty()) {
      throw new IllegalArgumentException("Compiler sources can not be empty");
    }
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    if (compiler == null) {
      throw new IOException("Compiler unavailable");
    }
    DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
    try (StandardJavaFileManager fileManager = compiler
        .getStandardFileManager(diagnostics, null, null);) {

      // 获取要编译的编译单元
      Iterable<? extends JavaFileObject> compilationUnits = fileManager
          .getJavaFileObjectsFromFiles(sourceFileList);
      JavaCompiler.CompilationTask compilationTask = compiler
          .getTask(null, fileManager, diagnostics, options, null, compilationUnits);
      // 运行编译任务
      boolean success = compilationTask.call();
      if (!success) {
        return diagnostics.getDiagnostics();
      }
    }
    return Collections.emptyList();
  }

  /**
   * compile java source list
   *
   * @param sources java source list
   * @param options options for javac
   * @return empty if compile success, otherwise error diagnostics
   * @throws IOException any compile exception
   */
  public static List<Diagnostic<? extends JavaFileObject>> compileJavaSources(
      List<? extends JavaSourceFromString> sources, Iterable<String> options)
      throws IOException {
    if (sources == null || sources.isEmpty()) {
      throw new IllegalArgumentException("Compiler sources can not be empty");
    }
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    if (compiler == null) {
      throw new IOException("Compiler unavailable");
    }
    DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
    /*
     * 编译选项，在编译java文件时，编译程序会自动的去寻找java文件引用的其他的java源文件或者class。
     * -sourcepath 选项就是定义java源文件的查找目录，有时我们编译一个Java源程式文件，而这个源程式文件需要另几个Java文件，
     *            而这些Java文件又在另外一个目录，那么这就需要为编译器指定这些文件所在的目录。
     * -classpath选项就是定义class文件的查找目录。
     * -d 是用来指定存放编译生成的.class文件的路径
     */
    JavaCompiler.CompilationTask compilationTask = compiler
        .getTask(null, null, diagnostics, options, null, sources);
    // 运行编译任务
    boolean success = compilationTask.call();
    if (!success) {
      return diagnostics.getDiagnostics();
    }
    return Collections.emptyList();
  }

  public static class JavaSourceFromString extends SimpleJavaFileObject {

    final String code;

    public JavaSourceFromString(String name, String code) {
      super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
      this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
      return code;
    }
  }




  public static void main(String[] args) throws IOException {
    System.out.println(compileJavaFileList(Collections.singletonList(Paths.get(
        "/home/zeno/source/local/photon/tools/java-compiler-samples/src/main/java/xyz/flysium/photon/A.java")
            .toFile()),
        Paths.get("/home/zeno/source/local/photon/tools/java-compiler-samples/target/tmp")
            .toFile()));

    System.out.println(compileJavaSources(Arrays.asList(
        new JavaSourceFromString(
            "A",
            "package packageA; public class A { packageB.B b; }"),
        new JavaSourceFromString(
            "B",
            "package packageB; public class B { packageC.C c; }"),
        new JavaSourceFromString(
            "C",
            "package packageC; public class C { packageA.A a; }")
    ), Arrays.asList("-encoding", "UTF-8", "-source", "1.8", "-d",
        "/home/zeno/source/local/photon/tools/java-compiler-samples/target/tmp")));
  }

}
