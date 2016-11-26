package de.zooplus.converter.web.config;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by dragan
 */
@Configuration
@EnableCaching
public class CachingConfig implements CachingConfigurer{

    @Value("${EHCACHE_CACHE_EVICT_POLICY}")
    private String cachePolicy;

    @Value("${EHCACHE_CACHE_TTL_IN_SECONDS}")
    private int cacheTTL;

    @Value("${EHCACHE_CACHE_MAX_ENTRIES}")
    private int maxEntries;

    @Bean(destroyMethod = "shutdown")
    public CacheManager ehCacheManager() {
        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        config.addCache(cacheConfig("myCache"));
        return net.sf.ehcache.CacheManager.newInstance(config);
    }

    @Bean
    @Override
    public org.springframework.cache.CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager());
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

    private CacheConfiguration cacheConfig(String name) {
        CacheConfiguration config = new CacheConfiguration();
        config.setName(name);
        config.setMaxEntriesLocalHeap(maxEntries);
        config.setMemoryStoreEvictionPolicy(cachePolicy);
        config.setTimeToLiveSeconds(cacheTTL);
        return config;
    }

//    @Bean
//    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
//        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
//        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
//        cacheManagerFactoryBean.setShared(true);
//        return cacheManagerFactoryBean;
//    }

//    @Bean
//    @Override
//    public CacheManager cacheManager() {
//        List<CacheManager> cacheManagers = new ArrayList<>();
//        cacheManagers.add(ehCacheCacheManager());
//
//        CompositeCacheManager cacheManager = new CompositeCacheManager();
//        cacheManager.getCache("").get
//
//        cacheManager.setCacheManagers(cacheManagers);
//        cacheManager.setFallbackToNoOpCache(false);
//
//        return cacheManager;
//    }
//
//    @Bean
//    @Override
//    public KeyGenerator keyGenerator() {
//        return new SimpleKeyGenerator();
//    }
}
