package xyz.flysium.photon.mbean;

/**
 * MBean
 *
 * @author zeno
 */
public interface HelloMBean {

  String getName();

  void setName(String name);

  void printHello();

  void printHello(String whoName);
}
