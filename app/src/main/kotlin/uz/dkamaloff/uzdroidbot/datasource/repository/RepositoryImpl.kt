package uz.dkamaloff.uzdroidbot.datasource.repository

import org.mapdb.DBMaker
import org.mapdb.DataInput2
import org.mapdb.DataOutput2
import org.mapdb.Serializer
import java.util.concurrent.ConcurrentMap


class RepositoryImpl : Repository {

    companion object {
        private val serializer = object : Serializer<Entity> {
            override fun serialize(out: DataOutput2, value: Entity) {
                out.writeLong(value.userId)
                out.writeLong(value.messageId)
                out.writeUTF(value.captcha)
            }

            override fun deserialize(input: DataInput2, available: Int): Entity = Entity(
                userId = input.readLong(),
                messageId = input.readLong(),
                captcha = input.readUTF()
            )
        }
        private val db = DBMaker.memoryDB().make()
        private val map: ConcurrentMap<Long, Entity> = db.hashMap("captcha", Serializer.LONG, serializer).createOrOpen()
    }

    /**
     * @param userId    is user id
     * @param messageId is last Captcha message id
     * @param captcha   is last Captcha as string
     */
    override suspend fun saveCaptchaInfo(userId: Long, messageId: Long, captcha: String) {
        map[userId] = Entity(userId, messageId, captcha)
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
    override suspend fun getLastCaptchaInfo(userId: Long): Pair<String, Long>? {
        val entity = map[userId] ?: return null
        return entity.captcha to entity.messageId
    }

    /**
     * Remove all captcha entities
     *
     * @param userId is user id
     */
    override suspend fun removeCaptcha(userId: Long) {
        map.remove(userId)
    }

    private data class Entity(
        val userId: Long,
        val messageId: Long,
        val captcha: String
    )
}