package com.example.demo

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

private const val KEY = "json-key"
private val VALUE = JsonObject("11")

@SpringBootTest
class JsonRedisServiceTest: DemoApplicationTests() {
    @Autowired
    private lateinit var jsonRedisService: JsonRedisService
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup() {
        jsonRedisService.delete(KEY)
    }

    @Test
    fun `레디스에 캐시가 없으면 null을 반환한다`() {
        // When
        val actual = jsonRedisService.get(KEY)

        // Then
        assertThat(actual).isNull()
    }

    @Test
    fun `레디스에 캐시가 있으면 캐시를 반환한다`() {
        // Given
        jsonRedisService.set(KEY, VALUE)

        // When
        val cache = jsonRedisService.get(KEY)
        val actual = objectMapper.convertValue(cache, JsonObject::class.java)

        // Then
        assertThat(actual).isEqualTo(VALUE)
    }
}