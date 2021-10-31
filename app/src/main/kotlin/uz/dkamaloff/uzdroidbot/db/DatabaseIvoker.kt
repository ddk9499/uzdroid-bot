package uz.dkamaloff.uzdroidbot.db

import io.vertx.pgclient.PgException
import io.vertx.pgclient.PgPool
import uz.dkamaloff.uzdroidbot.utils.logger


typealias DatabaseRunner<T> = suspend PgPool.() -> T

interface DatabaseInvoke {
    suspend operator fun <T> invoke(command: DatabaseRunner<T>): T
}

class DatabaseInvokeImpl constructor(
    private val pgClient: PgPool,
) : DatabaseInvoke {

    private val log by logger()

    override suspend operator fun <T> invoke(command: DatabaseRunner<T>): T = try {
        command(pgClient)
    } catch (e: PgException) {
        log.error(e.message, e)
        throw Exception(e.errorMessage)
    } catch (e: Exception) {
        log.error(e.message, e)
        throw  e
    }
}
