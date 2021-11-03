package uz.dkamaloff.uzdroidbot.handlers

import com.github.kotlintelegrambot.dispatcher.handlers.CallbackQueryHandlerEnvironment
import com.github.kotlintelegrambot.entities.ChatId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import uz.dkamaloff.uzdroidbot.datasource.service.CaptchaService

class CallbackQueryHandler(
    private val appScope: CoroutineScope,
    private val captchaService: CaptchaService
) : (CallbackQueryHandlerEnvironment) -> Unit {

    override fun invoke(env: CallbackQueryHandlerEnvironment) : Unit = with(env) {
        appScope.launch {
            val callbackData = callbackQuery.data
            val chatID = env.callbackQuery.message?.chat?.id ?: return@launch
            val isAskNewCaptcha = callbackData.matches("^resend:captcha:userId:\\d+".toRegex())

            val lastInfo = captchaService.getLastCaptchaInfo(callbackQuery.from.id)
            if (isAskNewCaptcha && lastInfo != null) {
                captchaService.newCaptcha(bot, ChatId.fromId(chatID), env.callbackQuery.from)
            }
        }
    }
}