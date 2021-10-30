package uz.dkamaloff.uzdroidbot.configuration

import io.github.cdimascio.dotenv.dotenv


private val env = dotenv {
    directory = "./devops/.env"
}

/**
 * Application Configuration.
 *
 * @author Dostonbek Kamalov
 */
interface AppConfiguration {
    val token: String
}

/**
 * Implementation that uses env as default variables.
 *
 * @author Dostonbek Kamalov
 */
class DefaultAppConfiguration(
    override val token: String = env.get("BOT_TOKEN")
) : AppConfiguration
