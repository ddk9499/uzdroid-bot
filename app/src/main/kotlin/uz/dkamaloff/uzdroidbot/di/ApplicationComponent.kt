package uz.dkamaloff.uzdroidbot.di

import dagger.Component
import uz.dkamaloff.uzdroidbot.Application
import javax.inject.Singleton

/**
 * Component which configures all needed Dagger modules to run the application.
 */
@[Singleton Component(
    modules = [
        VertxModule::class,
        BotModule::class,
        VerticleModule::class,
        DatabaseModule::class,
        CoroutinesModule::class,
    ]
)]
interface ApplicationComponent {
    val application: Application
}
