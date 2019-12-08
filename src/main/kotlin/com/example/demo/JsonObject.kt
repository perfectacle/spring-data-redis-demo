package com.example.demo

import java.time.Instant

data class JsonObject(
        val field: String,
        val createdAt: Instant = Instant.now(),
        val enum: TestEnum = TestEnum.TEST
)

enum class TestEnum {
    API, TEST
}