package com.usman.config;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

    public final static String UNLESS_RESULT_NULL = "#result == null";

    public final static String CACHE_ONE_HOUR = "cacheOneHour";
    public final static String CACHE_ONE_MINUTE = "cacheOneMinute";

    public final static String CACHE_TEN_MINUTE = "cacheTenMinute";
    private final static String KEY_SEPERATOR = "#";

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(1))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration(CACHE_ONE_MINUTE,
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(1)))
                .withCacheConfiguration(CACHE_TEN_MINUTE,
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)))
                .withCacheConfiguration(CACHE_ONE_HOUR,
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(60)));
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {

            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getSimpleName());
            sb.append(KEY_SEPERATOR);
            sb.append(method.getName());
            sb.append(KEY_SEPERATOR);
            for (Object param : params) {
                sb.append(param.toString());
                sb.append(KEY_SEPERATOR);
            }

            String str = sb.toString();

            return str.substring(0, str.length() - 1);
        };
    }

}


