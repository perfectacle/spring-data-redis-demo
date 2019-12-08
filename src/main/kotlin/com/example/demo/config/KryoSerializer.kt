package com.example.demo.config

import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output
import com.esotericsoftware.kryo.pool.KryoFactory
import com.esotericsoftware.kryo.pool.KryoPool
import org.springframework.data.redis.serializer.RedisSerializer


class KryoSerializer: RedisSerializer<Any> {
    private val factory: KryoFactory = KryoFactory {
        Kryo()
    }

    private val pool: KryoPool = KryoPool.Builder(factory).softReferences().build()

    override fun serialize(t: Any?): ByteArray? {
        return pool.run {
            val output = Output(8096, -1)
            it.writeClassAndObject(output, t)
            output.toBytes()
        }
    }

    override fun deserialize(bytes: ByteArray?): Any? {
        return pool.run {
            if (bytes != null) {
                val input = Input(bytes)
                it.readClassAndObject(input)
            } else {
                bytes
            }
        }
    }
}