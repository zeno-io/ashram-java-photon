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

package xyz.flysium.photon.api.resolver.query;

import com.baomidou.mybatisplus.core.metadata.IPage;
import graphql.GraphQLException;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.Connection;
import graphql.relay.SimpleListConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.flysium.photon.dao.entity.Book;
import xyz.flysium.photon.dto.BookInput;
import xyz.flysium.photon.service.BookService;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
@Component
public class RootQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private BookService bookService;

    @Value("${application.hello.exception}")
    private Boolean exception;

    public String hello() {
        if (!exception) {
            return "Query hello world";
        }
        else {
            throw new GraphQLException("Test Exception ....");
        }
    }

    public Book bookById(int bookId) {
        return bookService.findById(bookId);
    }

    public List<Book> booksAll() {
        return bookService.findAll();
    }

    public List<Book> booksByInput(BookInput bookInput) {
        return bookService.findByInput(bookInput);
    }

    public Connection<Book> books(int first, int offset, DataFetchingEnvironment env) {
        //    Page<Book> page = bookService.findByPage(offset, first);
        //    return new SimpleListConnection<>(page.getResult()).get(env);
        IPage<Book> page = bookService.findByPage(offset, first);
        return new SimpleListConnection<>(page.getRecords()).get(env);
    }

}
