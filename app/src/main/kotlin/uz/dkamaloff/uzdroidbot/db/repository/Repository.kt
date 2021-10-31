package uz.dkamaloff.uzdroidbot.db.repository

import io.vertx.kotlin.coroutines.await
import io.vertx.sqlclient.Row
import io.vertx.sqlclient.RowSet
import io.vertx.sqlclient.Tuple
import uz.dkamaloff.uzdroidbot.db.DatabaseInvoke
import javax.inject.Inject


class Repository @Inject constructor(
    private val databaseInvoke: DatabaseInvoke
) {

    /**
     * @param userId    is user id
     * @param messageId is last Captcha message id
     * @param captcha   is last Captcha as string
     */
    suspend fun saveCaptchaInfo(userId: Long, messageId: Long, captcha: String): RowSet<Row> = databaseInvoke.invoke {
        preparedQuery(
            "SELECT * FROM public.generate_captcha($1, $2, $3)"
        ).execute(Tuple.of(userId, messageId, captcha)).await()
    }

    /**
     *  if result is null user has not captcha and
     *  user doesn't need confirm captcha message
     *
     * @param userId is user id
     *
     * @return Pair<String, Long>?
     *     Captcha info     -> String
     *     Last message id  -> Long
     */
    suspend fun getLastCaptchaInfo(userId: Long): Pair<String, Long>? = databaseInvoke.invoke {
        preparedQuery("SELECT * FROM captcha WHERE captcha.user_id = $1")
            .execute(Tuple.of(userId))
            .await()
            .map {
                it.getString("captcha") to it.getLong("message_id")
            }.firstOrNull()
    }

    /**
     * Remove all captcha entities
     *
     * @param userId is user id
     */
    suspend fun removeCaptcha(userId: Long): RowSet<Row> = databaseInvoke.invoke {
        preparedQuery("DELETE FROM captcha WHERE captcha.user_id = $1")
            .execute(Tuple.of(userId)).await()
    }
}