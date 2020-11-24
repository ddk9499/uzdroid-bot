package uz.dkamaloff.uzdroidbot.configuration

import io.heapy.komodo.config.dotenv.Dotenv
import java.nio.file.Paths

private val env = Dotenv(Paths.get("./devops/.env"))

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