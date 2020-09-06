package xyz.flysium.photon.api.dataloader;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderOptions;
import org.dataloader.stats.Statistics;
import org.springframework.scheduling.annotation.Scheduled;
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
        super(new AuthorBatchLoader(authorRepository),
            // TODO 设置 DataLoader的参数 ，特别注意的是 目前 cache是全局生效的
            //     在这里目前是跨请求的，https://github.com/graphql-java/java-dataloader#the-scope-of-a-data-loader-is-important
            //     如果有不同的需求，需要更改 GraphQLInstrumentationConfiguration#dataLoaderRegistry
            DataLoaderOptions.newOptions().setMaxBatchSize(-1).setCacheMap(new CustomCacheMap())
                .setCachingEnabled(true));
    }

    // TODO 仅做演示，生产中在这里并不需要
    @Scheduled(fixedDelay = 5000)
    public void scheduled() {
        Statistics statistics = this.getStatistics();
        System.out.println(String
            .format("load : %d, batch load: %d, cache hit: %d, cache hit ratio: %.2f", statistics.getLoadCount(),
                statistics.getBatchLoadCount(), statistics.getCacheHitCount(), statistics.getCacheHitRatio()));
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
