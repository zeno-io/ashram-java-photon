package xyz.flysium.photon.api.resolver.subcription;

import graphql.kickstart.tools.GraphQLSubscriptionResolver;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.flysium.photon.api.publisher.BookUpdatePublisher;
import xyz.flysium.photon.dao.entity.Book;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
@Component
public class RootSubscriptionResolver implements GraphQLSubscriptionResolver {

    @Autowired
    private BookUpdatePublisher bookUpdatePublisher;

    public Publisher<Book> registerBookUpdated() {
        return bookUpdatePublisher.getPublisher();
    }

}