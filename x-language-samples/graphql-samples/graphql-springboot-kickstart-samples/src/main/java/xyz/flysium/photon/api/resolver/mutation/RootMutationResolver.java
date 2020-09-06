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

package xyz.flysium.photon.api.resolver.mutation;

import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.flysium.photon.api.publisher.BookUpdatePublisher;
import xyz.flysium.photon.dao.entity.Book;
import xyz.flysium.photon.service.BookService;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
@Component
public class RootMutationResolver implements GraphQLMutationResolver {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookUpdatePublisher bookUpdatePublisher;

    public String hello() {
        return "Mutation hello world";
    }

    public Book createBook(String name, int pageCount) {
        final Book book = bookService.createBook(name, pageCount);
        bookUpdatePublisher.emit(book);
        return book;
    }

    public Book updateBook(int id, String name) {
        final Book book = bookService.updateBook(id, name);
        bookUpdatePublisher.emit(book);
        return book;
    }

    public boolean removeBook(int id) {
        return bookService.removeBook(id);
    }

}
