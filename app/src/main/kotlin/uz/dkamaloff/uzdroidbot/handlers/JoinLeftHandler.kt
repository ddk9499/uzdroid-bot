package uz.dkamaloff.uzdroidbot.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.dispatcher.handlers.MessageHandlerEnvironment
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.entities.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import uz.dkamaloff.uzdroidbot.datasource.service.CaptchaService
import kotlin.coroutines.resume

class JoinLeftHandler(
    private val appScope: CoroutineScope,
    private val captchaService: CaptchaService
) : MessageHandler {

    override fun invoke(env: MessageHandlerEnvironment): Unit = with(env) {
        appScope.launch {
            message.newChatMembers?.let { onNewUsers(bot, message, it) }
            message.leftChatMember?.let { onRemoveUser(bot, message, it) }
        }
    }

    private suspend fun onNewUsers(bot: Bot, message: Message, newChatMembers: List<User>) {
        removeMessage(bot, ChatId.fromId(message.chat.id), message.messageId)
        newChatMembers.forEach { captchaService.newCaptcha(bot, ChatId.fromId(message.chat.id), it) }
    }

    private suspend fun onRemoveUser(bot: Bot, message: Message, member: User) {
        val chatId = ChatId.fromId(message.chat.id)

        removeMessage(bot, chatId, message.messageId)
        captchaService.getLastCaptchaInfo(member.id)?.let { lastInfo ->
            removeMessage(bot, chatId, lastInfo.second)
        }
    }

    private suspend fun removeMessage(bot: Bot, chatId: ChatId, messageId: Long) =
        suspendCancellableCoroutine<Boolean> { cont ->
            bot.deleteMessage(chatId, messageId)
                .fold({
                    cont.resume(it)
                }, {
                    cont.resume(it.isSuccess)
                })
        }
}
