package uz.dkamaloff.uzdroidbot.verticle

import com.github.kotlintelegrambot.Bot
import io.vertx.kotlin.coroutines.CoroutineVerticle
import uz.dkamaloff.uzdroidbot.utils.logger
import javax.inject.Inject


class MainVerticle @Inject constructor(
    private val bot: Bot
) : CoroutineVerticle() {

    private val log by logger()

    override suspend fun start() {
        bot.startPolling()
        checkAuth()
    }

    private fun checkAuth() {
        val me = bot.getMe()
        val response = me.first
        val userInfo = response?.body()?.result

        requireNotNull(response) { "Token is wrong" }
        requireNotNull(userInfo) { "Token is wrong" }
        log.info("RUN BOT named : {}", userInfo.firstName)
    }

    override suspend fun stop() {
        bot.stopPolling()
        super.stop()
        log.info("stop MainVerticle")
    }
}