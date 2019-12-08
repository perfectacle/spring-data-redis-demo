package com.example.demo.config

import com.fasterxml.jackson.databind.ObjectMapper
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
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig(
        private val objectMapper: ObjectMapper
) {
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

            /**
             * By default, RedisCache and RedisTemplate are configured to use Java native serialization.
             * Java native serialization is known for allowing remote code execution caused by payloads
             * that exploit vulnerable libraries and classes injecting unverified bytecode.
             * Manipulated input could lead to unwanted code execution in the application during the deserialization step.
             * As a consequence, do not use serialization in untrusted environments.
             * In general, we strongly recommend any other message format (such as JSON) instead.
             *
             * 기본적으로 JdkSerializationRedisSerializer가 사용된다.
             * JdkSerializationRedisSerializer를 사용했을 때 redis-cli로 raw key/value를 보면
             * \xac\xed\x00\x05t\x00\x03key와 같이 이상한 유니코드 문자열이 들어간다.
             */
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

    @Bean
    fun jsonRedisTemplate(
            connectionFactory: RedisConnectionFactory
    ): RedisTemplate<String, Any> {
        return RedisTemplate<String, Any>().apply {
            setConnectionFactory(connectionFactory)
            keySerializer = StringRedisSerializer()

            // objectMapper를 주입하지 않으면 레디스에서 값을 꺼내올 때
            // com.fasterxml.jackson.databind.exc.InvalidTypeIdException 던진다.
            valueSerializer = GenericJackson2JsonRedisSerializer(objectMapper)
        }
    }

    @Bean
    fun kryoRedisTemplate(
            connectionFactory: RedisConnectionFactory
    ): RedisTemplate<String, Any> {
        return RedisTemplate<String, Any>().apply {
            setConnectionFactory(connectionFactory)
            keySerializer = StringRedisSerializer()
            valueSerializer = KryoSerializer()
        }
    }
}