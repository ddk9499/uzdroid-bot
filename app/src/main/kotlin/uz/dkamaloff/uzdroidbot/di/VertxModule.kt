package uz.dkamaloff.uzdroidbot.di

import dagger.Module
import dagger.Provides
import io.micrometer.core.instrument.binder.system.UptimeMetrics
import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry
import io.vertx.core.DeploymentOptions
import io.vertx.core.Verticle
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.core.spi.VerticleFactory
import io.vertx.ext.web.client.WebClient
import io.vertx.micrometer.MicrometerMetricsOptions
import io.vertx.micrometer.VertxPrometheusOptions
import uz.dkamaloff.uzdroidbot.utils.jsonConfig
import uz.dkamaloff.uzdroidbot.utils.retrieveConfig
import javax.inject.Provider
import javax.inject.Singleton


@Module
object VertxModule {

    @[Provides Singleton]
    fun provideVertx(
        vertxOptions: VertxOptions,
        verticleFactory: VerticleFactory,
    ): Vertx {
        val vertx = Vertx.vertx(vertxOptions)
        vertx.registerVerticleFactory(verticleFactory)
        return vertx
    }

    @Provides
    fun provideVertxOptions(metricsOptions: MicrometerMetricsOptions): VertxOptions {
        return VertxOptions().setMetricsOptions(metricsOptions)
    }

    @Provides
    fun provideMicrometer(): MicrometerMetricsOptions {
        val registry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
        UptimeMetrics().bindTo(registry)

        return MicrometerMetricsOptions()
            .setPrometheusOptions(VertxPrometheusOptions().setEnabled(true))
            .setJvmMetricsEnabled(true)
            .setMicrometerRegistry(registry)
            .setEnabled(true)
    }

    @[Provides Singleton JvmSuppressWildcards]
    fun provideVerticleFactory(verticleMap: Map<Class<*>, Provider<Verticle>>): VerticleFactory =
        DaggerVerticleFactory(verticleMap)

    @[Provides Singleton]
    fun provideDeploymentOptions(vertx: Vertx): DeploymentOptions {
        val properties = jsonConfig("bot.json")
        return DeploymentOptions().apply {
            this.config = retrieveConfig(vertx, properties)
        }
    }

    @[Provides Singleton]
    fun provideWebClient(vertx: Vertx): WebClient = WebClient.create(vertx)
}
