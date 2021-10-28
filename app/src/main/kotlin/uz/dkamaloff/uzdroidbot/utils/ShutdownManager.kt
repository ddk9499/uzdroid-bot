package uz.dkamaloff.uzdroidbot.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

/**
 * Used for graceful shutdown resources.
 *
 * @author Dostonbek Kamalov
 */
interface ShutdownManager {
    fun onShutdown(callback: ShutdownCallback)
}

typealias ShutdownCallback = suspend () -> Unit

class DefaultShutdownManager : ShutdownManager {
    init {
        Runtime.getRuntime().addShutdownHook(thread(start = false) {
            LOGGER.info("Stopping bot")
            runBlocking {
                callbacks.map {
                    async(Dispatchers.Default) {
                        it()
                    }
                }.forEach { it.await() }
                LOGGER.info("Bot stopped")
            }
        })
    }

    private val callbacks: MutableList<ShutdownCallback> = mutableListOf()

    override fun onShutdown(callback: ShutdownCallback) {
        callbacks.add(callback)
    }

    companion object {
        private val LOGGER = logger<DefaultShutdownManager>()
    }
}