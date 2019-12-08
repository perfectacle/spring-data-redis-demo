package com.example.demo

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisService(
        private val redisTemplate: RedisTemplate<String, String>
) {
    private val valueOperations = redisTemplate.opsForValue()

    fun set(key: String, value: String) {
        valueOperations[key] = value
    }

    fun get(key: String): String? {
        return valueOperations[key]
    }

    fun delete(key: String) {
        redisTemplate.delete(key)
    }
}