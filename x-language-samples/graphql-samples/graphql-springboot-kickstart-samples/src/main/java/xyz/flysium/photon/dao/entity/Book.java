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

package xyz.flysium.photon.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class Book {

  @TableId(type = IdType.AUTO)
  private int id;
  private String name;

  private int pageCount;

//  private OffsetDateTime createdAt;
private LocalDateTime createdAt;

  private int authorId;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPageCount() {
    return pageCount;
  }

  public void setPageCount(int pageCount) {
    this.pageCount = pageCount;
  }

  //  public OffsetDateTime getCreatedAt() {
  //    return createdAt;
  //  }
  //
  //  public void setCreatedAt(OffsetDateTime createdAt) {
  //    this.createdAt = createdAt;
  //  }
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public int getAuthorId() {
    return authorId;
  }

  public void setAuthorId(int authorId) {
    this.authorId = authorId;
  }

  public static final class BookBuilder {
    private Book book;

    private BookBuilder() {
      book = new Book();
    }

    public static BookBuilder aBook() {
      return new BookBuilder();
    }

    public BookBuilder id(int id) {
      book.setId(id);
      return this;
    }

    public BookBuilder name(String name) {
      book.setName(name);
      return this;
    }

    public BookBuilder pageCount(int pageCount) {
      book.setPageCount(pageCount);
      return this;
    }

    //    public BookBuilder createdAt(OffsetDateTime createdAt) {
    //      book.setCreatedAt(createdAt);
    //      return this;
    //    }
    public BookBuilder createdAt(LocalDateTime createdAt) {
      book.setCreatedAt(createdAt);
      return this;
    }

    public BookBuilder authorId(int authorId) {
      book.setAuthorId(authorId);
      return this;
    }

    public Book build() {
      return book;
    }
  }
}
