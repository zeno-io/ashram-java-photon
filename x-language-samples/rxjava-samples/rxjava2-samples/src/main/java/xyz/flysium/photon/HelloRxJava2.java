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

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * RxJava2
 *
 * <li> NotSupport Backpressure (PUSH): Observable -> Consumer | Observer (async) </li>
 * <li> Support Backpressure (PULL):  Flowable -> Consumer | Subscriber (async) </li>
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class HelloRxJava2 {

  @Test
  public void testObservableConsumerSynchronization() throws InterruptedException {
    System.out.println("---------------");
    System.out.println(Thread.currentThread().getName());

    // 创建一个可被观察者的对象，数据类型是 String
    Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
      @Override
      public void subscribe(ObservableEmitter<String> emitter) throws Exception {
        emitter.onNext("hello");
        emitter.onNext("hello, the time is " + nowString());
      }
    });

    // PUSH
    Disposable d = observable.delay(1, TimeUnit.SECONDS).subscribe(new Consumer<String>() {
      @Override
      public void accept(String data) {
        System.out
            .println(Thread.currentThread().getName() + " at " + nowString() + " , accept message: "
                + data);
      }
    });

    TimeUnit.SECONDS.sleep(1);

    d.dispose();
  }

  @Test
  public void testObservableConsumerAsynchronization() throws InterruptedException {
    System.out.println("---------------");
    System.out.println(Thread.currentThread().getName());

    // 创建一个可被观察者的对象，数据类型是 String
    Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
      @Override
      public void subscribe(ObservableEmitter<String> emitter) throws Exception {
        emitter.onNext("hello");
        emitter.onNext("hello, the time is " + nowString());
      }
    });

    // PUSH
    Disposable d = observable.observeOn(Schedulers.single()).delay(1, TimeUnit.SECONDS)
        .subscribe(new Consumer<String>() {
          @Override
          public void accept(String data) {
            System.out
                .println(
                    Thread.currentThread().getName() + " at " + nowString() + " , accept message: "
                        + data);
          }
        });

    TimeUnit.SECONDS.sleep(1);

    d.dispose();
  }


  @Test
  public void testObservableObserverAsynchronization() throws InterruptedException {
    System.out.println("---------------");
    System.out.println(Thread.currentThread().getName());

    // 创建一个可被观察者的对象，数据类型是 String
    Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
      @Override
      public void subscribe(ObservableEmitter<String> emitter) throws Exception {
        emitter.onNext("hello");
        emitter.onNext("hello, the time is " + nowString());
      }
    });

    // PUSH
    observable.observeOn(Schedulers.single()).delay(1, TimeUnit.SECONDS)
        .subscribe(new Observer<String>() {

          // 推送时候
          @Override
          public void onSubscribe(Disposable d) {
            System.out
                .println(Thread.currentThread().getName() + " at " + nowString() + " , disposable: "
                    + d);
          }

          // 接收到数据
          @Override
          public void onNext(String data) {
            System.out
                .println(
                    Thread.currentThread().getName() + " at " + nowString() + " , accept message: "
                        + data);
          }

          // 异常时
          @Override
          public void onError(Throwable throwable) {
            throwable.printStackTrace();
          }

          // 完成时
          @Override
          public void onComplete() {
            System.out.println("over !");
          }

        });

    TimeUnit.SECONDS.sleep(1);
  }


  @Test
  public void testFlowableSubscriberAsynchronization() throws InterruptedException {
    System.out.println("---------------");
    System.out.println(Thread.currentThread().getName());

    // 创建一个可被观察者的对象，数据类型是 String
    Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
      @Override
      public void subscribe(FlowableEmitter<String> emitter) throws Exception {
        emitter.onNext("hello");
        emitter.onNext("hello, the time is " + nowString());
      }
    }, BackpressureStrategy.BUFFER);

    // PULL
    flowable.observeOn(Schedulers.single()).delay(1, TimeUnit.SECONDS)
        .subscribe(new Subscriber<String>() {

          // 推送时候
          @Override
          public void onSubscribe(Subscription subscription) {
            System.out
                .println(
                    Thread.currentThread().getName() + " at " + nowString() + " , subscription: "
                        + subscription);
          }

          // 接收到数据
          @Override
          public void onNext(String data) {
            System.out
                .println(
                    Thread.currentThread().getName() + " at " + nowString() + " , accept message: "
                        + data);
          }

          // 异常时
          @Override
          public void onError(Throwable throwable) {
            throwable.printStackTrace();
          }

          // 完成时
          @Override
          public void onComplete() {
            System.out.println("over !");
          }

        });

    TimeUnit.SECONDS.sleep(1);
  }


  private String nowString() {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }

}
