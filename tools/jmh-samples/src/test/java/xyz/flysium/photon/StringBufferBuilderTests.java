/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.flysium.photon;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

/**
 * benchmark Tests for String, StringBuilder, StringBuffer
 *
 * @author zeno
 */
@BenchmarkMode(Mode.AverageTime) //基准测试类型
@Warmup(iterations = 3) //预热的迭代次数
@Measurement(iterations = 3)    // 度量:iterations进行测试的轮次，time每轮进行的时长，timeUnit时长单位,batchSize批次数量
@Threads(6) // 测试线程数量
@Fork(10) // 测试进程数量
@OutputTimeUnit(TimeUnit.MILLISECONDS) //基准测试结果的时间类型
@State(Scope.Benchmark)
public class StringBufferBuilderTests {

  @Benchmark
  public String testString() {
    String buf = "";
    for (int i = 0; i < 10000; i++) {
      buf += i;
    }
    return buf;
  }

  @Benchmark
  public String testStringBuffer() {
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < 10000; i++) {
      buf.append(i);
    }
    return buf.toString();
  }

  @Benchmark
  public String testStringBuilder() {
    StringBuilder buf = new StringBuilder();
    for (int i = 0; i < 10000; i++) {
      buf.append(i);
    }
    return buf.toString();
  }

//# Run complete. Total time: 00:04:08
//
//Benchmark                                   Mode  Cnt   Score   Error  Units
//StringBufferBuilderTests.testString         avgt   50  13.982 ± 0.212  ms/op
//StringBufferBuilderTests.testStringBuffer   avgt   50   0.099 ± 0.002  ms/op
//StringBufferBuilderTests.testStringBuilder  avgt   50   0.095 ± 0.008  ms/op

}
