package uz.dkamaloff.uzdroidbot.utils

import nl.captcha.Captcha
import nl.captcha.backgrounds.TransparentBackgroundProducer
import nl.captcha.noise.CurvedLineNoiseProducer
import nl.captcha.text.producer.DefaultTextProducer
import nl.captcha.text.renderer.DefaultWordRenderer
import java.io.File
import javax.imageio.ImageIO


object CaptchaUtil {

    private val textProducer by lazy { DefaultTextProducer() }
    private val wordRenderer by lazy { DefaultWordRenderer() }
    private val noiseProducer by lazy { CurvedLineNoiseProducer() }
    private val backgroundProducer by lazy { TransparentBackgroundProducer() }

    operator fun invoke(): Captcha = generate(150, 80)

    fun generate(width: Int, height: Int): Captcha {
        return Captcha.Builder(width, height)
            .addBackground(backgroundProducer)
            .addText(textProducer, wordRenderer)
            .addNoise(noiseProducer).build();
    }

    fun toFile(captcha: Captcha): File {
        val outputStream = File.createTempFile("captcha", "png")
        ImageIO.write(captcha.image, "png", outputStream)
        return outputStream
    }
}