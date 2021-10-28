package uz.dkamaloff.uzdroidbot.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import io.vertx.core.Verticle
import uz.dkamaloff.uzdroidbot.verticle.MainVerticle


@Module
interface VerticleModule {

    @Binds
    @IntoMap
    @ClassKey(value = MainVerticle::class)
    fun bindMainVerticle(
        verticle: MainVerticle
    ): Verticle
}
