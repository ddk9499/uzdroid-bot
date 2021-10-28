package uz.dkamaloff.uzdroidbot.di

import io.vertx.core.Promise
import io.vertx.core.Verticle
import io.vertx.core.spi.VerticleFactory
import java.util.concurrent.Callable
import javax.inject.Provider


class DaggerVerticleFactory(private val verticleMap: Map<Class<*>, Provider<Verticle>>) : VerticleFactory {

    override fun createVerticle(verticleName: String, classLoader: ClassLoader?, promise: Promise<Callable<Verticle>>) {
        val verticle = verticleMap
            .getOrElse(sanitizeVerticleClassName(verticleName)) {
                throw IllegalStateException("No provider for verticle type $verticleName found")
            }.get()
        promise.complete { verticle }
    }

    private fun sanitizeVerticleClassName(verticleName: String): Class<*> {
        val verticle = verticleName.substring(verticleName.lastIndexOf(":") + 1)
        return Class.forName(verticle)
    }

    override fun prefix(): String = "dagger"
}
