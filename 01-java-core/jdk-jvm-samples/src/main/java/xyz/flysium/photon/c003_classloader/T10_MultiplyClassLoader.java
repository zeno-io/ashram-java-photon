package xyz.flysium.photon.c003_classloader;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T10_MultiplyClassLoader {

    static final String PATH = "/home/zeno/source/local/photon/java-core-samples/jdk-jvm-samples/target/classes";

    public static void main(String[] args) throws Throwable {
        final String name1 = "xyz.flysium.photon.c003_classloader.T10_MultiplyClassLoader$Test1";
        final String name2 = "xyz.flysium.photon.c003_classloader.T10_MultiplyClassLoader$Test2";
        URL[] urls = new URL[] { new URL("file", "", PATH)
        };
        MyAppClassLoader classLoader11 = new MyAppClassLoader(urls);
        final Class<?> clazz11 = classLoader11.loadClass(name1);
        Method m11 = clazz11.getDeclaredMethod("m");
        Method setVar11 = clazz11.getDeclaredMethod("setVar", int.class);
        final Object inst11 = clazz11.getDeclaredConstructor().newInstance();

        MyAppClassLoader classLoader12 = new MyAppClassLoader(urls);
        final Class<?> clazz12 = classLoader12.loadClass(name1);
        Method m12 = clazz12.getDeclaredMethod("m");
        Method setVar12 = clazz12.getDeclaredMethod("setVar", int.class);
        final Object inst12 = clazz12.getDeclaredConstructor().newInstance();

        MyAppClassLoader classLoader21 = new MyAppClassLoader(urls);
        final Class<?> clazz21 = classLoader21.loadClass(name2);
        Method m21 = clazz21.getDeclaredMethod("m");
        Method setVar21 = clazz21.getDeclaredMethod("setVar", int.class);
        final Object inst21 = clazz21.getDeclaredConstructor().newInstance();

        m11.invoke(inst11);
        m12.invoke(inst12);
        m21.invoke(inst21);

        System.out.println();
        System.out.println();

        m11.invoke(inst12);
        setVar12.invoke(inst12, 3);
        //        setVar21.invoke(inst12, 3);
        m11.invoke(inst12);
    }

    static class Test1 {

        private int a = 1;

        public void setVar(int a) {
            this.a = a;
        }

        public void m() {
            System.out.println("Test 1 -> " + this.a);
            System.out.println("Test 1 -> " + this);
            System.out.println("Test 1 -> " + Thread.currentThread().getContextClassLoader());
        }
    }

    static class Test2 {

        private int a = 2;

        public void setVar(int a) {
            this.a = a;
        }

        public void m() {
            System.out.println("Test 2 -> " + this.a);
            System.out.println("Test 2 -> " + this);
            System.out.println("Test 2 -> " + Thread.currentThread().getContextClassLoader());
        }
    }

    static class MyAppClassLoader extends URLClassLoader {

        public MyAppClassLoader(URL[] urls) {
            super(urls);
        }

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            return super.loadClass(name);
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            return super.findClass(name);
        }
    }
}
