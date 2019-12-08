package com.example.demo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

private const val KEY = "kryo-key"
private val VALUE = JsonObject()

@SpringBootTest
class KryoRedisServiceTest {
    @Autowired
    private lateinit var kryoRedisService: KryoRedisService

    @BeforeEach
    fun setup() {
        kryoRedisService.delete(KEY)
    }

    @Test
    fun `레디스에 캐시가 없으면 null을 반환한다`() {
        // When
        val actual = kryoRedisService.get(KEY)

        // Then
        assertThat(actual).isNull()
    }

    @Test
    fun `레디스에 캐시가 있으면 캐시를 반환한다`() {
        // Given
        kryoRedisService.set(KEY, VALUE)

        // When
        val actual = kryoRedisService.get(KEY) as JsonObject

        // Then
        assertThat(actual).isEqualTo(VALUE)
    }
}