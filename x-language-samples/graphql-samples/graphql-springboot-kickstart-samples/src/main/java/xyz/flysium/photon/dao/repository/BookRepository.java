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

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import xyz.flysium.photon.dao.entity.Book;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
@Component
@Mapper
public interface BookRepository extends BaseMapper<Book> {

  //  private static final List<Book> books = Arrays.asList(
  //      BookBuilder.aBook().id(1).name("Harry Potter and the Philosopher's Stone").pageCount(223)
  //          .authorId(1).build(),
  //      BookBuilder.aBook().id(2).name("Moby Dick").pageCount(635)
  //          .authorId(2).build(),
  //      BookBuilder.aBook().id(3).name("c").pageCount(371)
  //          .authorId(3).build()
  //  );

  @Select("select ID, NAME, PAGE_COUNT AS PAGECOUNT, CREATED_AT AS CREATEDAT, AUTHOR_ID AS AUTHORID from book where id = #{bookId}")
  public Book findById(@Param("bookId") int bookId);/* {
    return books
        .stream()
        .filter(book -> book.getId() == bookId)
        .findFirst()
        .orElse(null);
  }*/

  @Select("select  ID, NAME, PAGE_COUNT AS PAGECOUNT, CREATED_AT AS CREATEDAT, AUTHOR_ID AS AUTHORID from book where name like concat(concat('%', #{name}), '%')")
  public List<Book> findByName(@Param("name") String name);/* {
    return books
        .stream()
        .filter(book -> book.getName().contains(name))
        .collect(Collectors.toList());
  }*/

  @Select("select  ID, NAME, PAGE_COUNT AS PAGECOUNT, CREATED_AT AS CREATEDAT, AUTHOR_ID AS AUTHORID from book where 1=1 ")
  public List<Book> findAll(); /*{
    return books;
  }*/

}
