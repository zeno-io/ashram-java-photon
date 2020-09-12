package xyz.flysium.photon;

import java.io.IOException;
import java.util.Set;
import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import xyz.flysium.photon.mbean.HelloMBean;

/**
 * Java Client for JMX
 *
 * @author zeno
 */
public class Client {

  public static void main(String[] args) throws IOException,
      MalformedObjectNameException, InstanceNotFoundException,
      AttributeNotFoundException, InvalidAttributeValueException,
      MBeanException, ReflectionException, IntrospectionException {
    final String domainName = "MyMBean";
    final int rmiPort = 1099;
    JMXServiceURL url = new JMXServiceURL(
        "service:jmx:rmi:///jndi/rmi://localhost:" + rmiPort + "/" + domainName);

    // (1) 通过 JMXConnector 创建连接
    // 可以类比 HelloAgent.java 中的那句：
    // JMXConnectorServer jmxConnector = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
    JMXConnector jmxc = JMXConnectorFactory.connect(url);
    MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

    // print domains
    System.out.println("Domains:------------------");
    String[] domains = mbsc.getDomains();
    for (int i = 0; i < domains.length; i++) {
      System.out.println("\tDomain[" + i + "] = " + domains[i]);
    }
    // MBean count
    System.out.println("MBean count = " + mbsc.getMBeanCount());
    // process attribute
    ObjectName mBeanName = new ObjectName(domainName + ":name=HelloWorld");
    mbsc.setAttribute(mBeanName, new Attribute("Name", "宗离"));// 注意这里是Name 而不是 name
    System.out.println("Name = " + mbsc.getAttribute(mBeanName, "Name"));

    // 接下去是执行Hello中的printHello方法，分别通过代理和 rmi 的方式执行
    // via proxy
    HelloMBean proxy = MBeanServerInvocationHandler
        .newProxyInstance(mbsc, mBeanName, HelloMBean.class, false);
    proxy.printHello();
    proxy.printHello("zeno");
    // via rmi
    mbsc.invoke(mBeanName, "printHello", null, null);
    mbsc.invoke(mBeanName, "printHello", new String[]{"Sven Augustus"},
        new String[]{String.class.getName()});

    // get mbean information
    MBeanInfo info = mbsc.getMBeanInfo(mBeanName);
    System.out.println("Hello Class: " + info.getClassName());
    for (int i = 0; i < info.getAttributes().length; i++) {
      System.out.println("Hello Attribute: " + info.getAttributes()[i].getName());
    }
    for (int i = 0; i < info.getOperations().length; i++) {
      System.out.println("Hello Operation: " + info.getOperations()[i].getName());
    }

    // ObjectName of MBean
    System.out.println("All ObjectName:--------------");
    Set<ObjectInstance> set = mbsc.queryMBeans(null, null);
    for (ObjectInstance oi : set) {
      System.out.println("\t" + oi.getObjectName());
    }
    jmxc.close();
  }
}
