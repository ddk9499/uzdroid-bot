package uz.dkamaloff.uzdroidbot.di

import dagger.Module
import dagger.Provides
import io.vertx.core.Vertx
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext


@Module
object CoroutinesModule {

    @[Provides Singleton]
    fun provideDispatcherIO(vertx: Vertx): CoroutineContext = vertx.dispatcher()

    @[Provides Singleton]
    fun provideAppCoroutineScope(
        context: CoroutineContext
    ): CoroutineScope = CoroutineScope(SupervisorJob() + context)
}