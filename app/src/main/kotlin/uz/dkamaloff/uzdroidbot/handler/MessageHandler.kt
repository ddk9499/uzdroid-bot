package uz.dkamaloff.uzdroidbot.handler

import com.github.kotlintelegrambot.dispatcher.handlers.MessageHandlerEnvironment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import uz.dkamaloff.uzdroidbot.service.UtilService
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MessageHandler @Inject constructor(
    private val utilService: UtilService,
    private val coroutineScope: CoroutineScope,
) : (MessageHandlerEnvironment) -> Unit {

    override fun invoke(env: MessageHandlerEnvironment) {
        coroutineScope.launch {
            val message = env.message
            val bot = env.bot

            message.newChatMembers?.let { utilService.onNewUsers(bot, message, it) }
            message.leftChatMember?.let { utilService.onRemoveUser(bot, message, it) }

            utilService.handleMessage(bot, message)
        }
    }
}