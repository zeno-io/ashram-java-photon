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

package xyz.flysium.photon.c000_bio.s06_parse;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;

/**
 * StreamTokenizer测试
 *
 * @author Sven Augustus
 * @version 2017年1月28日
 */
public class StreamTokenizerTest {

	public static void main(String[] args) throws IOException {
		StreamTokenizer tokenizer = new StreamTokenizer(
				new StringReader("Sven had 7 shining ring..."));
		/**
		 * nval 如果当前标记是一个数字，则此字段将包含该数字的值。 sval 如果当前标记是一个文字标记，则此字段包含一个给出该文字标记的字符的字符串。
		 * TT_EOF 指示已读到流末尾的常量。 TT_EOL 指示已读到行末尾的常量。 TT_NUMBER 指示已读到一个数字标记的常量。 TT_WORD
		 * 指示已读到一个文字标记的常量。 ttype 在调用 nextToken 方法之后，此字段将包含刚读取的标记的类型。
		 */
		while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {// 流末尾
			if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
				System.out.println(tokenizer.sval);
			} else if (tokenizer.ttype == StreamTokenizer.TT_NUMBER) {
				System.out.println(tokenizer.nval);
			} else if (tokenizer.ttype == StreamTokenizer.TT_EOL) {// 行末尾
				System.out.println();
			}
		}
		// System.out.println(tokenizer.lineno());
	}

}
