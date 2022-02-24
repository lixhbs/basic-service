package com.basic.admin.common.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author sys
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@Slf4j
public class RedisConfig
{
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisCounterTemplate(LettuceConnectionFactory redisConnectionFactory)
    {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(stringRedisSerializer);
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }
}
