@file:Suppress("unused")

package uz.dkamaloff.uzdroidbot.utils

import io.vertx.config.ConfigRetriever
import io.vertx.config.ConfigRetrieverOptions
import io.vertx.config.ConfigStoreOptions
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


fun propertiesConfig(path: String): ConfigStoreOptions = ConfigStoreOptions().apply {
    type = "file"
    format = "properties"
    config = JsonObject().apply {
        put("path", path)
    }
}

fun jsonConfig(path: String): ConfigStoreOptions = ConfigStoreOptions().apply {
    type = "file"
    format = "json"
    config = JsonObject().apply {
        put("path", path)
    }
}

fun ymlConfig(path: String): ConfigStoreOptions = ConfigStoreOptions().apply {
    type = "file"
    format = "yml"
    config = JsonObject().apply {
        put("path", path)
    }
}

fun envConfig(): ConfigStoreOptions = ConfigStoreOptions().apply { type = "env" }

fun retrieveConfig(vertx: Vertx, vararg stores: ConfigStoreOptions): JsonObject = runBlocking(vertx.dispatcher()) {
    suspendCancellableCoroutine { cont ->
        val options = ConfigRetrieverOptions().apply {
            this.stores = stores.toList().plus(envConfig())
        }

        ConfigRetriever
            .create(vertx, options)
            .getConfig {
                if (!it.failed()) cont.resume(it.result())
                else cont.resumeWithException(it.cause())
            }
    }
}