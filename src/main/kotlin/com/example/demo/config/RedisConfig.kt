package com.example.demo.config

import io.lettuce.core.ReadFrom
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConfiguration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {
    @Bean
    fun serverConfig(): RedisConfiguration {
        return RedisStandaloneConfiguration("localhost")
    }

    @Bean
    fun clientConfig(): LettuceClientConfiguration {
        return LettuceClientConfiguration
                .builder()
                .readFrom(ReadFrom.MASTER_PREFERRED)
                .build()
    }

    @Bean
    fun connectionFactory(
            serverConfig: RedisConfiguration,
            clientConfig: LettuceClientConfiguration
    ): RedisConnectionFactory {
        return LettuceConnectionFactory(serverConfig, clientConfig)
    }

    @Bean
    fun redisTemplate(
            connectionFactory: RedisConnectionFactory
    ): RedisTemplate<String, String> {
        return RedisTemplate<String, String>().apply {
            setConnectionFactory(connectionFactory)

            // key/value serializer를 설정하지 않아도 어플리케이션이 돌아가는데는 문제가 없다.
            // 하지만 redis-cli로 raw key/value를 보면 \xac\xed\x00\x05t\x00\x03key와 같이 이상한 유니코드 문자열이 들어간다.
            keySerializer = StringRedisSerializer()
            valueSerializer = StringRedisSerializer()
        }
    }

    // RedisAutoConfiguration 클래스에 stringRedisTemplate bean이 이미 정의돼있기 때문에 이름을 다르게 설정
    @Bean
    fun simpleStringRedisTemplate(
            connectionFactory: RedisConnectionFactory
    ): RedisTemplate<String, String> {
        // String Redis Template은 key/value serializer를 설정하지 않아도 기본적으로 StringRedisSerializer가 세팅된다.
        return StringRedisTemplate(connectionFactory)
    }
}