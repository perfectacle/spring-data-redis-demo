package com.example.demo

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class JsonRedisService(
        private val jsonStringRedisTemplate: RedisTemplate<String, Any>
) {
    private val valueOperations = jsonStringRedisTemplate.opsForValue()

    fun set(key: String, value: Any) {
        valueOperations[key] = value
    }

    fun get(key: String): Any? {
        return valueOperations[key]
    }

    fun delete(key: String) {
        jsonStringRedisTemplate.delete(key)
    }
}