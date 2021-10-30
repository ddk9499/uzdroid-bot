package uz.dkamaloff.uzdroidbot.handlers

import com.github.kotlintelegrambot.dispatcher.handlers.HandleMessage
import com.github.kotlintelegrambot.entities.ChatId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class JoinLeftHandler(private val appScope: CoroutineScope) : MessageHandler {

    override val handler: HandleMessage
        get() = {
            appScope.launch {
                message.leftChatMember?.let { _ ->
                    bot.deleteMessage(
                        chatId = ChatId.fromId(message.chat.id),
                        messageId = message.messageId
                    )
                }

                message.newChatMembers?.let { _ ->
                    bot.deleteMessage(
                        chatId = ChatId.fromId(message.chat.id),
                        messageId = message.messageId
                    )
                }
            }
        }

}
