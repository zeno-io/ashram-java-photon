package xyz.flysium.photon;

import com.sun.jdmk.comm.HtmlAdaptorServer;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import xyz.flysium.photon.mbean.Hello;

/**
 * JMX Agent (Server)
 *
 * @author zeno
 */
public class HelloAgent {

  public static void main(String[] args) throws MalformedObjectNameException,
      NotCompliantMBeanException, InstanceAlreadyExistsException,
      MBeanRegistrationException, IOException {

    // （1）建立一个MBeanServer
    // 首先建立一个MBeanServer,MBeanServer用来管理我们的MBean,通常是通过MBeanServer来获取我们MBean的信息，间接
    // 调用MBean的方法，然后生产我们的资源的一个对象。
    MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
    // 下面这种方式不能 在 JConsole中使用 MBeanServer server = MBeanServerFactory.createMBeanServer();

    final String domainName = "MyMBean";

    // (2) 注册 MBean
    // 为 MBean（下面的new Hello()）创建 ObjectName 实例
    ObjectName helloName = new ObjectName(domainName + ":name=HelloWorld");
    // 将 new Hello() 这个对象注册到 MBeanServer 上去
    mbs.registerMBean(new Hello(), helloName);

    // (3) Distributed Layer
    // 提供了一个 HtmlAdaptor。支持 Http访问协议，并且有一个不错的HTML界面，这里的 Hello 就是用这个作为远端管理的界面
    // 事实上 HtmlAdaptor 是一个简单的 HttpServer ，它将 Http 请求转换为 JMX Agent的请求
    ObjectName adapterName = new ObjectName(domainName + ":name=htmladapter,port=8082");
    HtmlAdaptorServer adapter = new HtmlAdaptorServer();
    adapter.start();
    mbs.registerMBean(adapter, adapterName);

    // （4）通过 RMI 暴露端口
    int rmiPort = 1099;
    Registry registry = LocateRegistry.createRegistry(rmiPort);

    JMXServiceURL url = new JMXServiceURL(
        "service:jmx:rmi:///jndi/rmi://localhost:" + rmiPort + "/" + domainName);
    JMXConnectorServer jmxConnector = JMXConnectorServerFactory
        .newJMXConnectorServer(url, null, mbs);
    jmxConnector.start();
  }

}
