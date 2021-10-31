package uz.dkamaloff.uzdroidbot.handler

import com.github.kotlintelegrambot.dispatcher.handlers.HandleCommand
import com.github.kotlintelegrambot.entities.*
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import uz.dkamaloff.uzdroidbot.utils.CaptchaGenerator
import uz.dkamaloff.uzdroidbot.utils.logger
import java.io.File
import javax.imageio.ImageIO
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
                try {
                    val captcha = CaptchaGenerator().invoke(120, 60)
                    val outputStream = File.createTempFile("captcha", "png")
                    ImageIO.write(captcha.image, "png", outputStream)


                } catch (t: Throwable) {

                }
            }
        }


}