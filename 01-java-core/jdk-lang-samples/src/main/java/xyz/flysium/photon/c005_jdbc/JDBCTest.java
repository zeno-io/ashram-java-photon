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

package xyz.flysium.photon.c005_jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 阐述JDBC操作数据库的步骤。 下面的代码以连接本机的Oracle数据库为例，演示JDBC操作数据库的步骤。
 *
 * @author Sven Augustus
 * @version 2016年10月30日
 */
//如果需要建议连接，java中提供了一套标准，数据库厂商来进行实现，包含实现子类，实子类的jar文件再哪？
//一般情况下存放在安装目录下
public class JDBCTest {

  public static void main(String[] args) throws Exception {

    //1、加载驱动:
    /*
     * 当执行了当前代码之后，会返回一个Class对象，再此对象的创建过程中，会调用具体类的静态代码块
     * */
    Class.forName("oracle.jdbc.driver.OracleDriver");
    //2、建立连接
    //第一步中已经经driver对象初测到了drivermanager中，所以此时可以直接通过DriverManager来获取数据库的连接
    /*
     * 需要输入连接数据库的参数
     * url:数据库的地址
     * username:用户名
     * password:密码
     *
     * */
    Connection connection = DriverManager
        .getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
    //3、测试连接是否成功
    System.out.println(connection);
    //4、定义sql语句
    //只要填写正常执行的sql语句即可
    String sql = "select * from emp";
    //5、准备静态处理块对象，将sql语句放置到静态处理块中,理解为sql语句放置对象
    /*
     * 在执行sql语句的过程中，需要一个对象来存放sql语句，将对象进行执行的时候调用的是数据库的服务，数据库会从当前对象中
     * 拿到对应的sql语句进行执行
     *
     * */
    Statement statement = connection.createStatement();
    //6、执行sql语句,返回值对象是结果集合
    /*
     * 将结果放到resultset中，是返回结果的一个集合
     * 需要经过循环迭代才能获取到其中的每一条记录
     *
     * statement在执行的时候可以选择三种方式：
     * 1、execute:任何SQL语句都可以执行
     * 2、executeQueryL只能执行查询语句
     * 3、executeUpdate，只能执行语句
     * */
    ResultSet resultSet = statement.executeQuery(sql);
    //7、循环处理
    //使用while循环，有两种获取具体值的方式，第一种通过下表索引编号来获取，从1开始，第二种是通过列名来获取，哪种好？推荐使用列名，列明一般不会发生修改
    while (resultSet.next()) {
      int anInt = resultSet.getInt(1);
      System.out.println(anInt);
      String ename = resultSet.getString("ename");
      System.out.println(ename);
      System.out.println("-----------------");
    }
    //8、关闭连接
    statement.close();
    connection.close();
  }

}

