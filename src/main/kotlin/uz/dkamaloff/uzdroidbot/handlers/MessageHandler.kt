package uz.dkamaloff.uzdroidbot.handlers

import com.github.kotlintelegrambot.dispatcher.handlers.HandleMessage

interface MessageHandler {
    val handler: HandleMessage
}
