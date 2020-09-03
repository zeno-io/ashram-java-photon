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

package xyz.flysium.photon.c101_string;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Paths;

/**
 * encoding for String
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T01_StringEncodeDecode {

  static final Charset GBK = Charset.forName("GBK");
  static final Charset GB2312 = Charset.forName("GB2312");

  public static void main(String[] args) throws IOException {
    final String srcString = new String("s中文123456");

    // UTF to GBK
    final byte[] gbkBytes = srcString.getBytes(GBK);
    writeFile(gbkBytes, GBK);

    byte[] gbkBytesFromFile = readFile(GBK);
    final String gbkString = new String(gbkBytesFromFile);
    System.out.println("gbkString from gbk file -> " + gbkString);

    // GBK to UTF8
    final String utf8String = new String(gbkBytesFromFile, GBK);
    System.out.println("utf8String from gbk file-> " + utf8String);

    // GBK to GB2312
    writeFile(gbkBytesFromFile, GB2312);

    byte[] gb2312BytesFromFile = readFile(GB2312);
    System.out.println("utf8String from gb2312 file-> " + new String(gb2312BytesFromFile, GB2312));
  }

  protected static void writeFile(byte[] bytes, Charset charset) throws IOException {
    final String x = new String(bytes, charset);
    final File f = getFile(charset);
    try (PrintWriter pw = new PrintWriter(
        new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f),
            charset)))) {
      pw.write(x);
      pw.flush();
    }
  }

  protected static byte[] readFile(Charset charset) throws IOException {
    final File f = getFile(charset);
    byte[] bytes = new byte[(int) f.length()];
    try (FileInputStream fis = new FileInputStream(f)) {
      int i = fis.read(bytes);
      while (i > 0) {
        i = fis.read(bytes, i, bytes.length - i);
      }
    }
    return bytes;
  }

  private static File getFile(Charset charset) {
    return Paths.get("/var/tmp/" + charset.name() + ".txt").toFile();
  }

}
