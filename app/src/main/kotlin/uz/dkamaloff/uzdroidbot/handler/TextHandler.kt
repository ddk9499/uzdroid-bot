package uz.dkamaloff.uzdroidbot.handler

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.dispatcher.handlers.MessageHandlerEnvironment
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.entities.User
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import com.github.kotlintelegrambot.network.fold
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import uz.dkamaloff.uzdroidbot.db.repository.Repository
import uz.dkamaloff.uzdroidbot.utils.CaptchaGenerator
import uz.dkamaloff.uzdroidbot.utils.notnull
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume


@Singleton
class MessageHandler @Inject constructor(
    private val repository: Repository,
    private val coroutineScope: CoroutineScope,
    private val captchaGenerator: CaptchaGenerator,
) : (MessageHandlerEnvironment) -> Unit {

    override fun invoke(env: MessageHandlerEnvironment) {
        coroutineScope.launch {
            val message = env.message
            val bot = env.bot

            message.newChatMembers?.let { onNewUsers(bot, message, it) }
            message.leftChatMember?.let { onRemoveUser(bot, message, it) }

            val user = message.from ?: return@launch
            repository.getLastCaptchaInfo(user.id)?.let {
                if (!message.text.isNullOrBlank() && message.text == it.first) {
                    repository.removeCaptcha(user.id)
                    removeMessage(bot, ChatId.fromId(message.chat.id), it.second)
                    removeMessage(bot, ChatId.fromId(message.chat.id), message.messageId)
                } else removeMessage(bot, ChatId.fromId(message.chat.id), message.messageId)
            }
        }
    }

    private suspend fun onNewUsers(bot: Bot, message: Message, newChatMembers: List<User>) {
        removeMessage(bot, ChatId.fromId(message.chat.id), message.messageId)
        newChatMembers.forEach { newCaptcha(bot, ChatId.fromId(message.chat.id), it) }
    }

    private suspend fun onRemoveUser(bot: Bot, message: Message, member: User) {
        removeMessage(bot, ChatId.fromId(message.chat.id), message.messageId)
    }

    private suspend fun newCaptcha(bot: Bot, chatId: ChatId, user: User) {
        notnull(repository.getLastCaptchaInfo(user.id)) { lastInfo ->
            removeMessage(bot, chatId, lastInfo.second)
        }

        val caption = captchaGenerator(150, 80)
        val inlineKeyboardMarkup = InlineKeyboardMarkup.createSingleButton(
            InlineKeyboardButton.CallbackData(text = "Test Inline Button", callbackData = "testButton")
        )

        val response = bot.sendPhoto(
            chatId = chatId,
            caption = getCaption(user),
            photo = captchaGenerator.toFile(caption),
            replyMarkup = inlineKeyboardMarkup
        )

        notnull(response.first?.body()?.result) { message ->
            repository.saveCaptchaInfo(user.id, message.messageId, caption.answer)
        }
    }

    private suspend fun removeMessage(bot: Bot, chatId: ChatId, messageId: Long) =
        suspendCancellableCoroutine<Boolean> { cont ->
            bot.deleteMessage(chatId, messageId)
                .fold({
                    cont.resume(it?.result == true)
                }, {
                    cont.resume(false)
                })
        }

    private fun getCaption(user: User): String {
        val userName = user.firstName.ifBlank { user.username ?: user.firstName }

        return """
            User $userName
            Please send captcha
        """.trimIndent()
    }
}