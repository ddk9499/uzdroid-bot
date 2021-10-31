package uz.dkamaloff.uzdroidbot

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import uz.dkamaloff.uzdroidbot.configuration.DefaultAppConfiguration
import uz.dkamaloff.uzdroidbot.utils.logger

/**
 * Entry point of bot.
 *
 * @author Dostonbek Kamalov
 */
object Application {

    private val LOGGER = logger<Application>()

    @JvmStatic
    fun main(args: Array<String>) {
        try {
            val appConfiguration = DefaultAppConfiguration()
            val shutdownManager = DefaultShutdownManager()
            val appScope = CoroutineScope(SupervisorJob())

            val bot = UzDroidBot(appConfiguration.token, appScope)
            bot.startPolling()
            shutdownManager.onShutdown { bot.stopPolling() }

            LOGGER.info("Bot started.")
        } catch (e: Exception) {
            LOGGER.error("Error in bot.", e)
        }
    }
}
