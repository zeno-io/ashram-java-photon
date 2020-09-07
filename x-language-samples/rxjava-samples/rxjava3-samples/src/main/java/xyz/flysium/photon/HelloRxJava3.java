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

package xyz.flysium.photon;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import io.reactivex.rxjava3.observers.DisposableMaybeObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * RxJava : https://github.com/ReactiveX/RxJava
 *
 * <li>支持 Java 8+ Lambda API</li>
 * <li>基类
 *  <ul>Flowable：发送0个N个的数据，支持Reactive-Streams和背压</ul>
 *  <ul>Observable：发送0个N个的数据，不支持背压，</ul>
 *  <ul>Single：只能发送单个数据或者一个错误</ul>
 *  <ul>Completable：没有发送任何数据，但只处理 onComplete 和 onError 事件。</ul>
 *  <ul>Maybe：能够发射0或者1个数据，要么成功，要么失败。</ul>
 * </li>
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class HelloRxJava3 {

  // 大佬们，一波RxJava 3.0来袭，请做好准备~ :  https://juejin.im/post/5d1eeffe6fb9a07f0870b4e8
  // RxJava3.0 入门教程:  https://www.cnblogs.com/chengyangyang/p/12058211.html
  // rxjava3——背压:  https://qiankunli.github.io/2018/07/31/rxjava3.html

  @Test
  public void whatIsNewVsRxJava2() throws InterruptedException {
    Flowable.just("Hello world").subscribe(System.out::println);
    System.out.println("---------------");

    Flowable.create(emitter -> {
      emitter.onNext("hello1");
      emitter.onNext("hello2");
      emitter.onNext("hello3");
      emitter.onNext("hello4");
    }, BackpressureStrategy.BUFFER).subscribe(System.out::println);
    System.out.println("---------------");

    Observable.just("hello1", "hello2").delay(1, TimeUnit.SECONDS)
        .subscribe(System.out::println);

    TimeUnit.SECONDS.sleep(1);
    System.out.println("---------------");

    Single.just("single").subscribe(System.out::println);
    System.out.println("---------------");

    Completable.complete()
        .delay(1, TimeUnit.SECONDS, Schedulers.io())
        .subscribeWith(new DisposableCompletableObserver() {
          @Override
          public void onStart() {
            System.out.println("Started");
          }

          @Override
          public void onError(Throwable error) {
            error.printStackTrace();
          }

          @Override
          public void onComplete() {
            System.out.println("Done!");
          }
        });

    TimeUnit.SECONDS.sleep(1);
    System.out.println("---------------");

    Disposable maybeObserver = Maybe.just("maybe")
        .delay(3, TimeUnit.SECONDS, Schedulers.io())
        .subscribeWith(new DisposableMaybeObserver<String>() {
          @Override
          public void onStart() {
            System.out.println("Started");
          }

          @Override
          public void onSuccess(String value) {
            System.out.println("Success: " + value);
          }

          @Override
          public void onError(Throwable error) {
            error.printStackTrace();
          }

          @Override
          public void onComplete() {
            System.out.println("Done!");
          }
        });

    TimeUnit.SECONDS.sleep(5);

    maybeObserver.dispose();
  }

}
