package com.example.demo.config

import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Configuration
import redis.embedded.RedisServer

@Configuration
class EmbeddedRedisConfig: InitializingBean, DisposableBean {
    lateinit var server: RedisServer

    override fun afterPropertiesSet() {
        server = RedisServer(6379)
        server.start()
    }

    override fun destroy() {
        server.stop()
    }
}