package org.matoujun.cloud.common.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author matoujun
 */
@Configuration
public class CacheConfig {
    public static final int DEFAULT_MAXSIZE = 50000;
    public static final int DEFAULT_TTL = 10;


    public enum Caches {
        apiConfig(30, 10000, new String[]{"apiAccess"});

        Caches(int ttl, int maxSize, String[] keys) {
            this.ttl = ttl;
            this.maxSize = maxSize;
            this.keys = keys;
        }

        private int maxSize = DEFAULT_MAXSIZE;
        private int ttl = DEFAULT_TTL;
        private String[] keys;

        public int getMaxSize() {
            return maxSize;
        }

        public int getTtl() {
            return ttl;
        }

        public String[] getKeys() {
            return keys;
        }
    }

    @Bean
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        ArrayList<CaffeineCache> caches = new ArrayList<CaffeineCache>();
        for (Caches c : Caches.values()) {
            String[] keys = c.getKeys();
            for (String k : keys) {
                caches.add(new CaffeineCache(k,
                        Caffeine.newBuilder().recordStats()
                                .expireAfterWrite(c.getTtl(), TimeUnit.SECONDS)
                                .maximumSize(c.getMaxSize())
                                .build())
                );
            }
        }
        cacheManager.setCaches(caches);
        return cacheManager;
    }
}
