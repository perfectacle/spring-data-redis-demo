package com.example.demo

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisService(
        private val redisTemplate: RedisTemplate<String, String>
) {
    fun set(key: String, value: String) {
        val opsForValue = redisTemplate.opsForValue()
        opsForValue[key] = value
    }

    fun get(key: String): String? {
        val opsForValue = redisTemplate.opsForValue()
        return opsForValue[key]
    }

    fun delete(key: String) {
        redisTemplate.delete(key)
    }
}