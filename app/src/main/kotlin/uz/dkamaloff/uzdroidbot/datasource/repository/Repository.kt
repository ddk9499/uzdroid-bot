package uz.dkamaloff.uzdroidbot.datasource.repository

interface Repository {

    /**
     * @param userId    is user id
     * @param messageId is last Captcha message id
     * @param captcha   is last Captcha as string
     */
    suspend fun saveCaptchaInfo(userId: Long, messageId: Long, captcha: String)

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
    suspend fun getLastCaptchaInfo(userId: Long): Pair<String, Long>?

    /**
     * Remove all captcha entities
     *
     * @param userId is user id
     */
    suspend fun removeCaptcha(userId: Long)
}