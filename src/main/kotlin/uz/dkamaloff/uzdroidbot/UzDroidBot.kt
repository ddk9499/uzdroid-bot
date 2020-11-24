package uz.dkamaloff.uzdroidbot

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.logging.LogLevel

fun UzDroidBot(botToken: String) : Bot = bot {
    token = botToken
    logLevel = LogLevel.Error
    dispatch {
        command("hello") {
            bot.sendMessage(message.chat.id, "Hello ${message.from?.firstName}")
        }
    }
}