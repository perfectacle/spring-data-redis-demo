package com.example.demo

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class JsonRedisService(
        private val jsonRedisTemplate: RedisTemplate<String, Any>
) {
    private val valueOperations = jsonRedisTemplate.opsForValue()

    fun set(key: String, value: Any) {
        valueOperations[key] = value
    }

    fun get(key: String): Any? {
        return valueOperations[key]
    }

    fun delete(key: String) {
        jsonRedisTemplate.delete(key)
    }
}