package uz.dkamaloff.uzdroidbot.handler

import com.github.kotlintelegrambot.dispatcher.handlers.HandleMessage
import com.github.kotlintelegrambot.entities.ChatId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import uz.dkamaloff.uzdroidbot.utils.logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextHandler @Inject constructor(
    private val coroutineScope: CoroutineScope,
) {

    private val log by logger()

    val handleMessage: HandleMessage
        get() = {
            log.info("Left " + update.message?.leftChatMember.toString())
            log.info("Join " + update.message?.newChatMembers.toString())

            coroutineScope.launch {
                message.leftChatMember?.let { user ->
                    bot.deleteMessage(ChatId.fromId(message.chat.id), message.messageId)
                }

                message.newChatMembers?.let { users ->
                    bot.deleteMessage(ChatId.fromId(message.chat.id), message.messageId)
                }
            }
        }
}