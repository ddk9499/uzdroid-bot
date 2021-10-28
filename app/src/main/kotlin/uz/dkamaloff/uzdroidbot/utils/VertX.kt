@file:Suppress("unused")

package uz.dkamaloff.uzdroidbot.utils

import io.vertx.core.AsyncResult
import io.vertx.core.DeploymentOptions
import io.vertx.core.Verticle
import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.MessageCodec
import io.vertx.kotlin.coroutines.awaitEvent
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@DslMarker
annotation class FunctionMarker

@ExtensionMarker
suspend inline fun <reified T : Verticle> Vertx.deployVerticle(options: DeploymentOptions): String {
    val asyncResult = awaitEvent<AsyncResult<String>> { deployVerticle("dagger:${T::class.java.name}", options, it) }
    if (asyncResult.succeeded()) return asyncResult.result()
    else throw asyncResult.cause()
}


//////////////////////

inline fun <reified T> Json.createCodec(): MessageCodec<T, T> {
    return object : MessageCodec<T, T> {
        override fun encodeToWire(buffer: Buffer, s: T) {
            val byreArr = encodeToString(s).encodeToByteArray()
            buffer.appendInt(byreArr.size)
            buffer.appendBytes(byreArr)
        }

        override fun decodeFromWire(pos: Int, buffer: Buffer): T {
            try {
                val length = buffer.getInt(pos)
                val positionStart: Int = pos + 4
                val positionEnd: Int = positionStart + length
                return decodeFromString(buffer.getBytes(positionStart, positionEnd).decodeToString())
            } catch (serialization: SerializationException) {
                throw serialization
            }
        }

        override fun transform(s: T): T = s
        override fun systemCodecID(): Byte = -1
        override fun name(): String = "${T::class.simpleName} codec"
    }
}