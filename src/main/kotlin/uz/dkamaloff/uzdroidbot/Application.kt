package uz.dkamaloff.uzdroidbot

import uz.dkamaloff.uzdroidbot.configuration.DefaultAppConfiguration

/**
 * Entry point of bot.
 *
 * @author Dostonbek Kamalov
 */
object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        try {
            val appConfiguration = DefaultAppConfiguration()
            val bot = UzDroidBot(appConfiguration.token)
            bot.startPolling()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}