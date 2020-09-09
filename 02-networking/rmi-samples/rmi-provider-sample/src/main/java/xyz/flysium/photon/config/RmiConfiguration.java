package xyz.flysium.photon.config;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
@Configuration
public class RmiConfiguration {

    @Value("${application.rmi.port}")
    private int port;

    @Bean
    public Registry registry(List<Remote> remotes)
        throws AlreadyBoundException, MalformedURLException, RemoteException {
        // 远程主机远程对象注册表 Registry 的实例，并指定端口为 19090 ，这一步必不可少（Java默认端口是1099），
        // 必不可缺的一步，缺少注册表创建，则无法绑定对象到远程注册表上
        final Registry registry = LocateRegistry.createRegistry(port);
        // 把远程对象注册到 RMI 注册服务器上，并命名为 RDemoService
        // 绑定的 URL 标准格式为：rmi://host:port/name(其中协议名可以省略, 比如 Naming.bind("//localhost:19090/DemoService",demoServiceImpl); ）
        for (Remote remote : remotes) {
            final Class<?>[] interfaces = remote.getClass().getInterfaces();
            // TODO 设置暴露的服务名
            Naming.bind("rmi://localhost:" + port + "/" + interfaces[0].getSimpleName(), remote);
        }
        return registry;
    }

}
