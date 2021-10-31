package uz.dkamaloff.uzdroidbot

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.logging.LogLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import uz.dkamaloff.uzdroidbot.datasource.repository.Repository
import uz.dkamaloff.uzdroidbot.datasource.repository.RepositoryImpl
import uz.dkamaloff.uzdroidbot.datasource.service.CaptchaService
import uz.dkamaloff.uzdroidbot.handlers.CallbackQueryHandler
import uz.dkamaloff.uzdroidbot.handlers.DefaultMessageHandler
import uz.dkamaloff.uzdroidbot.handlers.JoinLeftHandler
import uz.dkamaloff.uzdroidbot.handlers.MessageHandler
import uz.dkamaloff.uzdroidbot.utils.showAlert

@Suppress("FunctionName")
fun UzDroidBot(botToken: String, appScope: CoroutineScope): Bot {
    val repository: Repository = RepositoryImpl()
    val captchaService: CaptchaService = CaptchaService(repository)

    val joinLeftHandler: MessageHandler = JoinLeftHandler(appScope, captchaService)
    val defaultMessageHandler: MessageHandler = DefaultMessageHandler(appScope, repository)
    val callbackQueryHandler: CallbackQueryHandler = CallbackQueryHandler(appScope, captchaService)

    return bot {
        token = botToken
        logLevel = LogLevel.Error
        dispatch {
            command("hello") {
                appScope.launch {
                    bot.deleteMessage(ChatId.fromId(message.chat.id), message.messageId)
                    bot.showAlert(ChatId.fromId(message.chat.id), "Hello ${message.from?.firstName}")
                }
            }

            message(joinLeftHandler)
            message(defaultMessageHandler)
            callbackQuery(handleCallbackQuery = callbackQueryHandler)
        }
    }
}
