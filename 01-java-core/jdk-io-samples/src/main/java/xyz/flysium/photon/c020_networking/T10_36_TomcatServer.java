/*
 * MIT License
 *
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package xyz.flysium.photon.c020_networking;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardWrapper;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.http11.AbstractHttp11JsseProtocol;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.net.NioChannel;
import org.apache.tomcat.util.net.NioEndpoint;
import org.apache.tomcat.util.net.SocketEvent;
import org.apache.tomcat.util.net.SocketWrapperBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.flysium.photon.c002_nio.s07_selector_group.NIOChannelUtil;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class T10_36_TomcatServer {

  protected static final Logger logger = LoggerFactory.getLogger(T10_36_TomcatServer.class);

  public static void main(String[] args) throws InterruptedException, LifecycleException {
//    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    Connector connector = new Connector(new AbstractHttp11JsseProtocol(new NioEndpoint() {

      @Override
      protected SocketChannel serverSocketAccept() throws Exception {
        SocketChannel client = super.serverSocketAccept();
        if (logger.isDebugEnabled()) {
          logger.debug("accept new client：" + NIOChannelUtil.getRemoteAddress(client));
        }
        return client;
      }

      @Override
      public boolean processSocket(SocketWrapperBase<NioChannel> socketWrapper, SocketEvent event,
          boolean dispatch) {
        if (logger.isDebugEnabled()) {
          logger.debug("ready read from client: " + NIOChannelUtil
              .getRemoteAddress(socketWrapper.getSocket().getIOChannel()));
        }
        return super.processSocket(socketWrapper, event, dispatch);
      }
    }) {

      @Override
      protected Log getLog() {
        return LogFactory.getLog(Http11NioProtocol.class);
      }

      @Override
      protected String getNamePrefix() {
        return "http-nio";
      }
    });

    connector.setPort(T10_0_C10KClient.SERVER_PORT);
    ProtocolHandler handler = connector.getProtocolHandler();
    if (handler instanceof AbstractProtocol) {
      ((AbstractProtocol) handler).setAcceptorThreadCount(1); // always 1
      ((AbstractProtocol) handler).setAcceptCount(T10_0_C10KClient.SERVER_BACKLOG);
//      ((Http11NioProtocol) handler).setPollerThreadCount(1); // always 1
      //  TODO 这里的Executor是Tomcat的业务线程池，这里设置为3，与 Netty对比 读写性能差异
      ((AbstractProtocol) handler).setMinSpareThreads(3);
      ((AbstractProtocol) handler).setMaxThreads(3);
//      ((AbstractProtocol) handler).setExecutor(T10_0_C10KClient.SERVER_EXECUTOR);
      // TODO Socket参数
      ((AbstractProtocol) handler).setTcpNoDelay(true);
      ((AbstractProtocol) handler).setConnectionLinger(100);
//      ((AbstractProtocol) handler).setConnectionTimeout(T10_0_C10KClient.SERVER_READ_TIMEOUT);
    }
    Tomcat tomcat = new Tomcat();
    tomcat.setBaseDir("/var/tmp");
    //    work
    //    └── Tomcat
    //        └── localhost
    //            └── ROOT
    tomcat.getService().addConnector(connector);
    tomcat.setConnector(connector);

    Context ctx = tomcat.addContext("", null);
    StandardWrapper wrapper = new StandardWrapper();
    wrapper.setServletName("MyHttpServlet");
    wrapper.setServletClass(MyHttpServlet.class.getName());
    ctx.addChild(wrapper);
    ctx.addServletMappingDecoded("/", "MyHttpServlet");

    tomcat.start();
  }

  public static class MyHttpServlet extends HttpServlet {

    private static final long serialVersionUID = 8376203146116945157L;

    public MyHttpServlet() {
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
      // read
      String encoding = req.getCharacterEncoding();
      if (encoding == null) {
        encoding = StandardCharsets.UTF_8.name();
      }
      byte[] bytes = new byte[8092];
      if (req.getQueryString() != null) {
        bytes = req.getQueryString().getBytes();
      } else {
        final int actual = IOUtils.read(req.getInputStream(), bytes, 0, bytes.length);
        if (actual == 0) {
          bytes = req.getRequestURI().getBytes();
        }
      }

      String requestString = new String(bytes, encoding);
      if (logger.isDebugEnabled()) {
        logger.debug("readied from client: " + req.getRemoteAddr()
            + ", data: " + requestString);
      }

      // FIXME 模拟业务逻辑处理时间耗时  应该使用业务线程池
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        logger.error(e.getMessage(), e);
      }

      // write
      String responseString = "recv->" + requestString;

      if (logger.isDebugEnabled()) {
        logger.debug(
            "ready write to client: " + req.getRemoteAddr());
      }

      resp.setContentType("text/html");
      resp.setCharacterEncoding(encoding);
      resp.getWriter().println(responseString);
    }

  }

}

