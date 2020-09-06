package xyz.flysium.photon.api.dataloader;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.springframework.stereotype.Component;
import xyz.flysium.photon.dao.entity.Author;
import xyz.flysium.photon.dao.repository.AuthorRepository;

/**
 *
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
@Component
public class AuthorDataLoader extends DataLoader<Integer, Author> {

    public AuthorDataLoader(AuthorRepository authorRepository) {
        super(new AuthorBatchLoader(authorRepository));
    }

    static class AuthorBatchLoader implements BatchLoader<Integer, Author> {

        private final AuthorRepository authorRepository;

        AuthorBatchLoader(AuthorRepository authorRepository) {
            this.authorRepository = authorRepository;
        }

        @Override
        public CompletionStage<List<Author>> load(List<Integer> keys) {
            return CompletableFuture.supplyAsync(() -> authorRepository.findAllById(keys));
        }
    }
}
