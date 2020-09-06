package xyz.flysium.photon.api.publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.observables.ConnectableObservable;
import org.springframework.stereotype.Component;
import xyz.flysium.photon.dao.entity.Book;

/**
 *
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
@Component
public class BookUpdatePublisher {

    private final Flowable<Book> publisher;

    private ObservableEmitter<Book> observableEmitter;

    public BookUpdatePublisher() {
        Observable<Book> stockPriceUpdateObservable = Observable.create(emitter -> {
            observableEmitter = emitter;
        });

        ConnectableObservable<Book> connectableObservable = stockPriceUpdateObservable.share().publish();
        connectableObservable.connect();

        publisher = connectableObservable.toFlowable(BackpressureStrategy.BUFFER);
    }

    public Flowable<Book> getPublisher() {
        return publisher;
    }

    public void emit(Book book) {
        observableEmitter.onNext(book);
    }
}
