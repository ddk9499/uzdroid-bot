package uz.dkamaloff.uzdroidbot

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.logging.LogLevel
import kotlinx.coroutines.CoroutineScope
import uz.dkamaloff.uzdroidbot.handlers.JoinLeftHandler

@Suppress("FunctionName")
fun UzDroidBot(botToken: String, appScope: CoroutineScope): Bot {
    val joinLeftHandler = JoinLeftHandler(appScope)

    return bot {
        token = botToken
        logLevel = LogLevel.Error
        dispatch {
            command("hello") {
                bot.sendMessage(ChatId.fromId(message.chat.id), "Hello ${message.from?.firstName}")
            }
            message(joinLeftHandler.handler)
        }
    }
}
