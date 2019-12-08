package com.example.demo

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class KryoRedisService(
        private val kryoRedisTemplate: RedisTemplate<String, Any>
) {
    private val valueOperations = kryoRedisTemplate.opsForValue()

    fun set(key: String, value: Any) {
        valueOperations[key] = value
    }

    fun get(key: String): Any? {
        return valueOperations[key]
    }

    fun delete(key: String) {
        kryoRedisTemplate.delete(key)
    }
}