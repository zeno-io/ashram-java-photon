package xyz.flysium.photon;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

/**
 * Hello MBean
 *
 * @author zeno
 */
@Component
@ManagedResource(objectName = "MyMBean:name=HelloWorld", description = "Hello My MBean")
public class HelloMBean {

  private String name;

  @ManagedAttribute(description = "Get name")
  public String getName() {
    return name;
  }

  @ManagedOperation
  @ManagedOperationParameter(name = "name", description = "Set name")
  public void setName(String name) {
    this.name = name;
  }

  @ManagedOperation
  public void printHello() {
    System.out.println("Hello world, " + name);
  }

  @ManagedOperation
  @ManagedOperationParameter(name = "whoName", description = "your name")
  public void printHello(String whoName) {
    System.out.println("Hello, " + whoName);
  }

}
