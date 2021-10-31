package uz.dkamaloff.uzdroidbot.handlers

import com.github.kotlintelegrambot.dispatcher.handlers.MessageHandlerEnvironment
import com.github.kotlintelegrambot.entities.ChatId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import uz.dkamaloff.uzdroidbot.datasource.repository.Repository
import uz.dkamaloff.uzdroidbot.utils.showAlert

class DefaultMessageHandler(
    private val appScope: CoroutineScope,
    private val repository: Repository
) : MessageHandler {

    override fun invoke(env: MessageHandlerEnvironment): Unit = with(env) {
        val user = message.from ?: return

        appScope.launch {
            repository.getLastCaptchaInfo(user.id)?.let {
                if (!message.text.isNullOrBlank() && message.text == it.first) {
                    repository.removeCaptcha(user.id)
                    bot.deleteMessage(ChatId.fromId(message.chat.id), it.second)
                    bot.deleteMessage(ChatId.fromId(message.chat.id), message.messageId)
                } else if (message.from?.isBot == false) {
                    bot.deleteMessage(ChatId.fromId(message.chat.id), message.messageId)
                    bot.showAlert(ChatId.fromId(message.chat.id), "⚠️ ${user.firstName} you don't auth", 10_000)
                }
            }
        }
    }
}
