package com.example.demo

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class StringRedisService(
        private val simpleStringRedisTemplate: RedisTemplate<String, String>
) {
    private val valueOperations = simpleStringRedisTemplate.opsForValue()

    fun set(key: String, value: String) {
        valueOperations[key] = value
    }

    fun get(key: String): String? {
        return valueOperations[key]
    }

    fun delete(key: String) {
        simpleStringRedisTemplate.delete(key)
    }
}