package com.example.demo

import java.time.Instant

data class JsonObject(
        val field: String = "11",
        val createdAt: Instant = Instant.now()
)