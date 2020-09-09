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

package xyz.flysium.photon.api.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import org.springframework.stereotype.Service;
import xyz.flysium.photon.api.DemoService;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
@Service
public class DemoServiceImpl extends UnicastRemoteObject implements DemoService {

    // 继承 UnicastRemoteObject 父类并实现自定义的远程接口
    // 因为 UnicastRemoteObject 的构造方法抛出了 RemoteException 异常，因此这里默认的构造方法必须写，必须声明抛出 RemoteException 异常

    public DemoServiceImpl() throws RemoteException {
    }

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }

}
