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

import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;
import java.util.concurrent.CompletableFuture;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.flysium.photon.api.dataloader.AuthorDataLoader;
import xyz.flysium.photon.dao.entity.Author;
import xyz.flysium.photon.dao.entity.Book;
import xyz.flysium.photon.dao.repository.AuthorRepository;
import xyz.flysium.photon.dao.repository.BookRepository;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
@Component
public class BookQueryResolver implements GraphQLResolver<Book> {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    /**
     * FiledResolver 字段解析  ->  author: Author
     * TODO 解决N+1问题，采用 DataLoader ->  GraphQLInstrumentationConfiguration
     */
    //    public Author author(Book book) {
    //        return authorRepository.findById(book.getAuthorId());
    //    }
    public CompletableFuture<Author> author(Book book, DataFetchingEnvironment environment) {
        DataLoader<Integer, Author> dataLoader = environment.getDataLoader(AuthorDataLoader.class.getSimpleName());
        final int key = book.getAuthorId();
        return dataLoader.load(key).whenComplete((r, e) -> {
            // TODO https://github.com/graphql-java/java-dataloader#caching-errors
            if (e != null) {
                dataLoader.clear(key);
                e.printStackTrace();
            }
        });
    }

}
