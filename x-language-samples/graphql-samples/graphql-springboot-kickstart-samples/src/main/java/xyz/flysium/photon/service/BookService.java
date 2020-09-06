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

package xyz.flysium.photon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.flysium.photon.dao.entity.Book;
import xyz.flysium.photon.dao.entity.Book.BookBuilder;
import xyz.flysium.photon.dao.repository.BookRepository;
import xyz.flysium.photon.dto.BookInput;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book findById(int bookId) {
        return bookRepository.findById(bookId);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findByInput(BookInput bookInput) {
        final String id = bookInput.getId();
        if (StringUtils.isNotEmpty(id)) {
            return Collections.singletonList(bookRepository.findById(NumberUtils.toInt(id)));
        }
        return bookRepository.findByName(bookInput.getName());
    }

    //  public Page<Book> findByPage(int offset, int limit) {
    //    return PageHelper.offsetPage(offset, limit).doSelectPage(() -> {
    //      bookRepository.findAll();
    //    });
    //  }

    public IPage<Book> findByPage(int offset, int limit) {
        Page<Book> page = new Page<>(offset, limit);
        return bookRepository.selectPage(page, null);
    }

    public Book createBook(String name, int pageCount) {
//        Book book = BookBuilder.aBook().name(name).pageCount(pageCount).createdAt(OffsetDateTime.now()).build();
        Book book = BookBuilder.aBook().name(name).pageCount(pageCount).createdAt(LocalDateTime.now()).build();
        int r = bookRepository.insert(book);
        if (r > 0) {
            final int id = book.getId();
            return bookRepository.findById(id);
        }
        return book;
    }

    public Book updateBook(int id, String name) {
        Book book = BookBuilder.aBook().id(id).name(name).build();
        int r = bookRepository.updateById(book);
        if (r > 0) {
            return bookRepository.findById(id);
        }
        return null;
    }

    public boolean removeBook(int id) {
        int r = bookRepository.deleteById(id);
        return r > 0;
    }

}
