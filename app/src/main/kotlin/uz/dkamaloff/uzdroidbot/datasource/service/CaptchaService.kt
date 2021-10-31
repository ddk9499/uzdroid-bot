package uz.dkamaloff.uzdroidbot.datasource.service

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.User
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import uz.dkamaloff.uzdroidbot.datasource.repository.Repository
import uz.dkamaloff.uzdroidbot.utils.CaptchaUtil

class CaptchaService(
    private val repository: Repository
) {

    suspend fun newCaptcha(bot: Bot, chatId: ChatId, user: User) {
        repository.getLastCaptchaInfo(user.id)?.let { lastInfo ->
            bot.deleteMessage(chatId, lastInfo.second)
        }

        val caption = CaptchaUtil()
        val inlineKeyboardMarkup = InlineKeyboardMarkup.createSingleButton(
            InlineKeyboardButton.CallbackData(
                text = "Resend captcha",
                callbackData = "resend:captcha:userId:${user.id}"
            )
        )

        val response = bot.sendPhoto(
            chatId = chatId,
            caption = getCaption(user),
            photo = CaptchaUtil.toFile(caption),
            replyMarkup = inlineKeyboardMarkup
        )

        response.first?.body()?.result?.let { message ->
            repository.saveCaptchaInfo(user.id, message.messageId, caption.answer)
        }
    }

    suspend fun getLastCaptchaInfo(userId: Long): Pair<String, Long>? = repository.getLastCaptchaInfo(userId)

    private fun getCaption(user: User): String {
        val userName = user.firstName.ifBlank { user.username ?: user.firstName }

        return """
            User $userName
            Please send captcha
        """.trimIndent()
    }
}