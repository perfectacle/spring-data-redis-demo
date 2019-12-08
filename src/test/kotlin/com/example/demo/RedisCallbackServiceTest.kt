package com.example.demo

import org.assertj.core.api.Assertions

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

private const val KEY = "callback-key"
private const val VALUE = "callback-value"

@SpringBootTest
class RedisCallbackServiceTest: DemoApplicationTests() {
    @Autowired
    private lateinit var redisCallbackService: RedisCallbackService

    @BeforeEach
    fun setup() {
        redisCallbackService.delete(KEY)
    }

    @Test
    fun `레디스에 캐시가 없으면 null을 반환한다`() {
        // When
        val actual = redisCallbackService.get(KEY)

        // Then
        Assertions.assertThat(actual).isNull()
    }

    @Test
    fun `레디스에 캐시가 있으면 캐시를 반환한다`() {
        // Given
        redisCallbackService.set(KEY, VALUE)

        // When
        val actual = redisCallbackService.get(KEY)

        // Then
        Assertions.assertThat(actual).isEqualTo(VALUE)
    }
}