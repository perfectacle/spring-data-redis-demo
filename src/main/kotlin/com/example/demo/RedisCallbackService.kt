package com.example.demo

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service


// RedisCallback 인터페이스를 사용하면 레디스와 다이렉트로 통신할 수 있다고 한다.
// 좀 더 덜 추상화 된 low-layer를 다룰 때 쓰면 될 듯... (성능 최적화 등등)
@Service
class RedisCallbackService(
        private val redisTemplate: RedisTemplate<String, String>
) {
    fun set(key: String, value: String) {
        redisTemplate.execute {
            it[key.toByteArray()] = value.toByteArray()
        }
    }

    fun get(key: String): String? {
        return redisTemplate.execute {
            val bytes = it[key.toByteArray()]
            if (bytes != null) {
                String(bytes)
            } else {
                bytes
            }
        }
    }

    fun delete(key: String) {
        redisTemplate.execute {
            it.del(key.toByteArray())
        }
    }
}