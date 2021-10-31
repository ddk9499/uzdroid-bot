package uz.dkamaloff.uzdroidbot.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.properties.ReadOnlyProperty


@DslMarker
annotation class ExtensionMarker

@ExtensionMarker
fun logger(): ReadOnlyProperty<Any, Logger> = ReadOnlyProperty { thisRef, _ ->
    LoggerFactory.getLogger(thisRef::class.java.canonicalName)
}

@ExtensionMarker
inline fun <reified T> logger(): Logger = LoggerFactory.getLogger(T::class.java.canonicalName)

@ExtensionMarker
inline fun <T> notnull(value: T?, body: (T) -> Unit) {
    if (value != null) body(value)
}
