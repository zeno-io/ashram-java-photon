package xyz.flysium.photon.c003_classloader;

/**
 * <code>Class.forName</code> 和 <code>ClassLoader</code> 的区别
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T11_ClassForName_Vs_ClassLoader {

    //  class.forName() 除了将类的.class文件加载到jvm中之外，还会对类进行解释，执行类中的static块。
    //  ClassLoader 只干一件事情，就是将.class文件加载到jvm中，不会执行 static 中的内容,只有在 newInstance() 才会去执行static块。
    //  Class.forName(name, initialize, loader) 带参函数也可控制是否加载 static 块。并且只有调用了 newInstance() 方法采用调用构造函数，创建类的对象

    static class T {
        static final int INT = 1;

        static {
            System.out.println("static ~");
        }
    }

    public static void main(String[] args) throws Throwable {
        final String className = "xyz.flysium.photon.c003_classloader.T11_ClassForName_Vs_ClassLoader$T";
        System.out.println("Class.forName -> ");

        Class.forName(className);

        System.out.println();
        System.out.println("ClassLoader -> ");

        Thread.currentThread().getContextClassLoader().loadClass(className);
    }

}
