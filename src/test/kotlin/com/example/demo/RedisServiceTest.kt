package com.example.demo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

const val KEY = "key"
const val VALUE = "value"

@SpringBootTest
class RedisServiceTest {
    @Autowired
    private lateinit var redisService: RedisService

    @BeforeEach
    fun setup() {
        redisService.delete(KEY)
    }

    @Test
    fun `레디스에 캐시가 없으면 null을 반환한다`() {
        // When
        val actual = redisService.get(KEY)

        // Then
        assertThat(actual).isNull()
    }

    @Test
    fun `레디스에 캐시가 있으면 캐시를 반환한다`() {
        // Given
        redisService.set(KEY, VALUE)

        // When
        val actual = redisService.get(KEY)

        // Then
        assertThat(actual).isEqualTo(VALUE)
    }
}