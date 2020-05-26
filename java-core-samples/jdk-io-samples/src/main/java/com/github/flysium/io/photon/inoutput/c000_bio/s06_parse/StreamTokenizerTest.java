/*
 * Apache License 2.0
 *
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.inoutput.c000_bio.s06_parse;

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
