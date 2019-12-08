package com.example.demo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

private const val KEY = "string-key"
private const val VALUE = "string-value"

@SpringBootTest
class StringRedisServiceTest: DemoApplicationTests() {
    @Autowired
    private lateinit var stringRedisService: StringRedisService

    @BeforeEach
    fun setup() {
        stringRedisService.delete(KEY)
    }

    @Test
    fun `레디스에 캐시가 없으면 null을 반환한다`() {
        // When
        val actual = stringRedisService.get(KEY)

        // Then
        assertThat(actual).isNull()
    }

    @Test
    fun `레디스에 캐시가 있으면 캐시를 반환한다`() {
        // Given
        stringRedisService.set(KEY, VALUE)

        // When
        val actual = stringRedisService.get(KEY)

        // Then
        assertThat(actual).isEqualTo(VALUE)
    }
}