package xyz.flysium.photon.api.dataloader;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;
import org.dataloader.CacheMap;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class CustomCacheMap<U, V> implements CacheMap<U, V> {

    private final Cache<U, V> cache;

    /**
     * Default constructor
     */
    public CustomCacheMap() {
        cache = CacheBuilder.newBuilder().maximumSize(1024).expireAfterAccess(60, TimeUnit.SECONDS).build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(U key) {
        return cache.getIfPresent(key) != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V get(U key) {
        return cache.getIfPresent(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CacheMap<U, V> set(U key, V value) {
        cache.put(key, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CacheMap<U, V> delete(U key) {
        cache.invalidate(key);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CacheMap<U, V> clear() {
        cache.invalidateAll();
        return this;
    }
}
