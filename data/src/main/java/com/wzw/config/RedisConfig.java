package com.wzw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置类
 *
 * @author: erenwu
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate(factory);
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        //调用后完成设置
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
