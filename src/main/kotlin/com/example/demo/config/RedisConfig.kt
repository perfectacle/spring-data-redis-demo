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
        val redisTemplate = RedisTemplate<String, String>()
        redisTemplate.setConnectionFactory(connectionFactory)
        return redisTemplate
    }
}