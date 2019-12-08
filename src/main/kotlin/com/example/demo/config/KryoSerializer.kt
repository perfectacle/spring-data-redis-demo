package com.example.demo.config

import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.Kryo.DefaultInstantiatorStrategy
import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output
import com.esotericsoftware.kryo.pool.KryoFactory
import com.esotericsoftware.kryo.pool.KryoPool
import org.objenesis.strategy.StdInstantiatorStrategy
import org.springframework.data.redis.serializer.RedisSerializer


class KryoSerializer: RedisSerializer<Any> {
    private val factory: KryoFactory = KryoFactory {
        val kryo = Kryo()
        // 기본 생성자가 없는데 아래 설정 마저도 없으면
        // com.esotericsoftware.kryo.KryoException: Class cannot be created (missing no-arg constructor)을 던짐.
        // 아래 설정은 먼저 기본 생성자로 객체 생성을 시도하고, 실패시에 생성자 없이 객체 생성시도하는 설정.
        kryo.instantiatorStrategy = DefaultInstantiatorStrategy(StdInstantiatorStrategy())
        kryo
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