package uz.dkamaloff.uzdroidbot.utils

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import kotlinx.coroutines.delay
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Function to retrieve logger by class.
 *
 * @author Dostonbek Kamalov
 */
inline fun <reified T : Any> logger(): Logger = LoggerFactory.getLogger(T::class.java.canonicalName)

suspend fun Bot.showAlert(chatId: ChatId, alert: String, delayMillis : Long = 3000) {
    val result = sendMessage(chatId, alert)
    (result.first?.body()?.result)?.let { message ->
        delay(delayMillis)
        deleteMessage(chatId, message.messageId)
    }
}