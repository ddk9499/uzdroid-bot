package uz.dkamaloff.uzdroidbot.handler

import com.github.kotlintelegrambot.dispatcher.handlers.CallbackQueryHandlerEnvironment
import com.github.kotlintelegrambot.entities.ChatId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import uz.dkamaloff.uzdroidbot.service.UtilService
import javax.inject.Inject

class CallbackQueryHandlerHandler @Inject constructor(
    private val utilService: UtilService,
    private val coroutineScope: CoroutineScope,
) : (CallbackQueryHandlerEnvironment) -> Unit {

    override fun invoke(env: CallbackQueryHandlerEnvironment) {
        coroutineScope.launch {
            val callbackData = env.callbackQuery.data
            val isAskNewCaptcha = callbackData.matches("^resend:captcha:userId:\\d+".toRegex())
            val chatID = env.callbackQuery.message?.chat?.id ?: return@launch
            val bot = env.bot

            if (isAskNewCaptcha) {
                utilService.newCaptcha(bot, ChatId.fromId(chatID), env.callbackQuery.from)
            }
        }
    }
}