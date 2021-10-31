package uz.dkamaloff.uzdroidbot.handler

import com.github.kotlintelegrambot.dispatcher.handlers.HandleCommand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import uz.dkamaloff.uzdroidbot.utils.logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommandHandler @Inject constructor(
    private val coroutineScope: CoroutineScope,
) {

    private val log by logger()

    val handleStart: HandleCommand
        get() = {
            log.info("Handle start command")
            coroutineScope.launch {

            }
        }

}