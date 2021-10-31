package uz.dkamaloff.uzdroidbot.handlers

import com.github.kotlintelegrambot.dispatcher.handlers.MessageHandlerEnvironment

interface MessageHandler : (MessageHandlerEnvironment) -> Unit
