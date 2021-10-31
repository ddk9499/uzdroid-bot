package uz.dkamaloff.uzdroidbot.di

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.dispatcher.telegramError
import dagger.Module
import dagger.Provides
import io.vertx.core.DeploymentOptions
import uz.dkamaloff.uzdroidbot.handler.CommandHandler
import uz.dkamaloff.uzdroidbot.handler.MessageHandler
import uz.dkamaloff.uzdroidbot.utils.logger
import javax.inject.Singleton


@Module
object BotModule {

    private val log by logger()

    @[Provides Singleton]
    fun provideBot(
        deploymentOptions: DeploymentOptions,
        messageHandler: MessageHandler,
        commandHandler: CommandHandler,
    ): Bot = bot {
        token = deploymentOptions
            .config
            .getJsonObject("BOT")
            .getString("TOKEN")

        dispatch {
            command("start", commandHandler.handleStart)
            message(messageHandler)
            telegramError { log.error(error.getErrorMessage()) }
        }
    }
}