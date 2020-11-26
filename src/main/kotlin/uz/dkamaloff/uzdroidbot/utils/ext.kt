package uz.dkamaloff.uzdroidbot.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Function to retrieve logger by class.
 *
 * @author Dostonbek Kamalov
 */
inline fun <reified T : Any> logger(): Logger = LoggerFactory.getLogger(T::class.java.canonicalName)