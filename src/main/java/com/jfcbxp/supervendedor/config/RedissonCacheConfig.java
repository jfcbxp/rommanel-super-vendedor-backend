package com.jfcbxp.supervendedor.config;

import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;


@Configuration
@RequiredArgsConstructor
public class RedissonCacheConfig {
    private final Environment env;

    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient){
        return new RedissonSpringCacheManager(redissonClient);
    }

    @Bean
    public RedissonClient getClient(){
            Config config = new Config();
            config.useSingleServer()
                    .setAddress(Objects.requireNonNull(env.getProperty("spring.redis.url")))
                    .setPassword(Objects.requireNonNull(env.getProperty("spring.redis.password")));
        return Redisson.create(config);
    }

}

