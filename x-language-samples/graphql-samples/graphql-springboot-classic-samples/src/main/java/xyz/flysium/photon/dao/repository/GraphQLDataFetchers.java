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

package xyz.flysium.photon.dao.repository;

import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
@Component
public class GraphQLDataFetchers {

  private static List<Map<String, String>> books = Arrays.asList(
      ImmutableMap.of("id", "book-1",
          "name", "Harry Potter and the Philosopher's Stone",
          "pageCount", "223",
          "authorId", "author-1"),
      ImmutableMap.of("id", "book-2",
          "name", "Moby Dick",
          "pageCount", "635",
          "authorId", "author-2"),
      ImmutableMap.of("id", "book-3",
          "name", "Interview with the vampire",
          "pageCount", "371",
          "authorId", "author-3")
  );
  private static List<Map<String, String>> authors = Arrays.asList(
      ImmutableMap.of("id", "author-1",
          "firstName", "Joanne",
          "lastName", "Rowling"),
      ImmutableMap.of("id", "author-2",
          "firstName", "Herman",
          "lastName", "Melville"),
      ImmutableMap.of("id", "author-3",
          "firstName", "Anne",
          "lastName", "Rice")
  );

  public DataFetcher getBookByIdDataFetcher() {
    // dataFetchingEnvironment 封装了查询中带有的参数
    return dataFetchingEnvironment -> {
      String bookId = dataFetchingEnvironment.getArgument("id");
      return books
          .stream()
          .filter(book -> book.get("id").equals(bookId))
          .findFirst()
          .orElse(null);
    };
  }


  public DataFetcher getAuthorDataFetcher() {
    // 这里因为是通过Book查询 Author 数据的子查询，所以 dataFetchingEnvironment.getSource() 中封装了Book对象的全部信息
    // 即GraphQL中每个字段的 Datafetcher 都是以自顶向下的方式调用的，父字段的结果是子Datafetcherenvironment的source属性。
    return dataFetchingEnvironment -> {
      Map<String, String> book = dataFetchingEnvironment.getSource();
      if (!book.containsKey("authorId")) {
        // TODO 截断额外的数据源查询，部分解决 N+1 问题
        return null;
      }
      String authorId = book.get("authorId");
      return authors
          .stream()
          .filter(author -> author.get("id").equals(authorId))
          .findFirst()
          .orElse(null);
    };
  }

  public DataFetcher getBooksDataFetcher() {
    // dataFetchingEnvironment 封装了查询中带有的参数
    return dataFetchingEnvironment -> {
      Map<String, Object> bookInput = dataFetchingEnvironment.getArgument("book");
      String bookId = MapUtils.getString(bookInput, "id");
      String bookName = MapUtils.getString(bookInput, "name");
      if (bookInput.containsKey("id")) {
        return books
            .stream()
            .filter(book -> book.get("id").equals(bookId))
            .findFirst()
            .orElse(null);
      }
      return books
          .stream()
          .filter(book -> bookName != null && book.get("name").contains(bookName))
          .collect(Collectors.toList());
    };
  }

}
