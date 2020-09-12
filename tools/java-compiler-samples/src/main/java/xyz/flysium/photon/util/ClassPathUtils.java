package xyz.flysium.photon.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.stream.Collectors;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.loader.data.RandomAccessDataFile;
import org.springframework.boot.loader.jar.Handler;
import org.springframework.boot.loader.jar.JarFile;

/**
 * Utils for classpath
 *
 * @author zeno
 */
public class ClassPathUtils {

  private static final Logger logger = LoggerFactory.getLogger(ClassPathUtils.class);

  // windows url 之间的分割是";" , linux 之间分割是":"
  private static final String COLON =
      System.getProperty("os.name").toUpperCase(Locale.ENGLISH).contains("WINDOWS") ? ";" : ":";

  /**
   * Get classpath for Spring Boot
   *
   * @return classpath
   * @throws IOException any IO Exception
   */
  public static String getClassPathForSpringBoot() throws IOException {
    return getClassPathForSpringBoot(true, null);
  }

  /**
   * Get classpath for Spring Boot
   *
   * @param decompressIfRepackage   decompress if repackage jar
   * @param decompressDirectoryName the directory name to decompress
   * @return classpath
   * @throws IOException any IO Exception
   */
  public static String getClassPathForSpringBoot(boolean decompressIfRepackage,
      String decompressDirectoryName) throws IOException {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    Set<String> allClassPaths = new TreeSet<>();
    if (classLoader instanceof URLClassLoader) {
      StringBuilder classPathString = new StringBuilder();
      final URL[] urLs = ((URLClassLoader) classLoader).getURLs();
      Set<File> rootFileSet = new TreeSet<>();
      for (URL urL : urLs) {
        if (!decompressIfRepackage) {
          allClassPaths.add(urL.getFile());
          continue;
        }
        File rootFile = getRootFileInSpringBoot(urL);
        if (rootFile != null && rootFileSet.add(rootFile)) {
          List<String> classPaths = decompressRootJarAndGetClassPaths(decompressDirectoryName,
              rootFile);
          allClassPaths.addAll(classPaths);
        }
      }
    } else {
      String classpath = System.getProperty("java.class.path");
      if (StringUtils.isNotEmpty(classpath)) {
        return classpath;
      }
    }
    StringBuffer buf = new StringBuffer();
    allClassPaths.forEach(c -> {
      buf.append(c).append(COLON);
    });
    return buf.toString();
  }

  private static File getRootFileInSpringBoot(URL urL) throws IOException {
    try {
      URLStreamHandler urlStreamHandler = getVariableValue(URL.class, "handler",
          URLStreamHandler.class, urL);
      if (urlStreamHandler instanceof org.springframework.boot.loader.jar.Handler) {
        org.springframework.boot.loader.jar.Handler handler = (Handler) urlStreamHandler;
        org.springframework.boot.loader.jar.JarFile jarFile = getVariableValue(Handler.class,
            "jarFile", org.springframework.boot.loader.jar.JarFile.class, handler);
        RandomAccessDataFile rootRandomAccessDataFile = getVariableValue(JarFile.class, "rootFile",
            RandomAccessDataFile.class, jarFile);
        File rootFile = rootRandomAccessDataFile.getFile();
        if (!Objects.equals(rootFile, Paths.get(urL.getFile()).toFile()) && rootFile.getName()
            .toLowerCase().endsWith(".jar")) {
          return rootFile;
        }
      }
    } catch (NoSuchFieldException | IllegalAccessException e) {
      logger.error(e.getMessage(), e);
    }
    return null;
  }

  private static <T> T getVariableValue(Class<?> clazz, String name, Class<?> type, Object inst)
      throws IllegalAccessException, NoSuchFieldException {
    VarHandle varHandle = MethodHandles
        .privateLookupIn(clazz, MethodHandles.lookup())
        .findVarHandle(clazz, name,
            type);
    return (T) varHandle.get(inst);
  }


  private static List<String> decompressRootJarAndGetClassPaths(String decompressDirectoryName,
      File rootJarFile) {
    List<String> classPaths = new ArrayList<>();
    File bootLib = Paths
        .get(rootJarFile.getParent(), StringUtils.defaultString(decompressDirectoryName, "BOOT"))
        .toFile();
    File[] files = bootLib.listFiles();
    if (files != null) {
      Arrays.stream(files).forEach(File::delete);
    }
    if (!bootLib.exists()) {
      bootLib.mkdirs();
    }
    try {
      JarFile rootJar = new JarFile(rootJarFile);
      String classes = getManifestAttributeOfJar(rootJar, BOOT_CLASSES_ATTRIBUTE);
      if (StringUtils.isNotEmpty(classes)) {
        classPaths.add(Paths.get(bootLib.getAbsolutePath(), classes).toFile().getAbsolutePath());
      }
      String location = getManifestAttributeOfJar(rootJar, BOOT_CLASSPATH_INDEX_ATTRIBUTE);
      if (StringUtils.isEmpty(location)) {
        return classPaths;
      }
    } catch (IOException e) {
      logger.error("Jar: {} parse error: {} ", rootJarFile.getAbsolutePath(), e);
      return classPaths;
    }
    try {
      String errorString = executeCommand(bootLib, " jar xf " + rootJarFile.getAbsolutePath());
      if (StringUtils.isNoneEmpty(errorString)) {
        logger.error("Jar: {} decompress error: {} ", rootJarFile.getAbsolutePath(), errorString);
        return Collections.emptyList();
      }
    } catch (IOException e) {
      logger.error("Jar: {} decompress error: {} ", rootJarFile.getAbsolutePath(), e);
    }
    List<File> fs = searchJarFiles(bootLib);
    fs.forEach(file -> {
      classPaths.add(file.getAbsolutePath());
    });
    return classPaths;
  }

  protected static final String BOOT_CLASSES_ATTRIBUTE = "Spring-Boot-Classes";

  protected static final String BOOT_CLASSPATH_INDEX_ATTRIBUTE = "Spring-Boot-Classpath-Index";

  private static final String DEFAULT_CLASSPATH_INDEX_LOCATION = "BOOT-INF/classpath.idx";

  private static String getManifestAttributeOfJar(JarFile archive, String attributeKey)
      throws IOException {
    Manifest manifest = archive.getManifest();
    Attributes attributes = (manifest != null) ? manifest.getMainAttributes() : null;
    return (attributes != null) ? attributes.getValue(attributeKey) : null;
  }

  private static List<File> searchJarFiles(File rootFile) {
    File[] listFiles = rootFile.listFiles();
    if (listFiles == null) {
      return Collections.emptyList();
    }
    List<File> fs = Arrays.stream(listFiles)
        .filter(f -> f.getName().toLowerCase().endsWith(".jar")).collect(Collectors.toList());
    Arrays.stream(listFiles).filter(File::isDirectory).forEach(f -> {
      fs.addAll(searchJarFiles(f));
    });
    return fs;
  }

  private static String executeCommand(File workingDirectory, String line) throws IOException {
    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      CommandLine commandLine = CommandLine.parse(line);
      ExecuteWatchdog watchdog = new ExecuteWatchdog(5 * 1000);

      PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(out);
      DefaultExecutor executor = new DefaultExecutor();
      executor.setStreamHandler(pumpStreamHandler);
      executor.setWatchdog(watchdog);
      executor.setWorkingDirectory(workingDirectory);
      int exitFlag = executor.execute(commandLine);
      if (exitFlag != 0) {
        return out.toString(StandardCharsets.UTF_8);
      }
    }
    return null;
  }

}
