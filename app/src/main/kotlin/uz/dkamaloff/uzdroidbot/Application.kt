package uz.dkamaloff.uzdroidbot

import io.micrometer.core.instrument.Meter
import io.micrometer.core.instrument.config.MeterFilter
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig
import io.micrometer.prometheus.PrometheusMeterRegistry
import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import io.vertx.kotlin.coroutines.await
import io.vertx.kotlin.coroutines.dispatcher
import io.vertx.micrometer.backends.BackendRegistries
import kotlinx.coroutines.runBlocking
import uz.dkamaloff.uzdroidbot.di.DaggerApplicationComponent
import uz.dkamaloff.uzdroidbot.utils.DefaultShutdownManager
import uz.dkamaloff.uzdroidbot.utils.deployVerticle
import uz.dkamaloff.uzdroidbot.utils.logger
import uz.dkamaloff.uzdroidbot.verticle.MainVerticle
import javax.inject.Inject

class Application @Inject constructor(
    private val vertx: Vertx,
    private val deploymentOptions: DeploymentOptions,
) {

    fun start() = runBlocking(vertx.dispatcher()) {
        val registry = BackendRegistries.getDefaultNow() as PrometheusMeterRegistry
        registry.config().meterFilter(
            object : MeterFilter {
                override fun configure(
                    id: Meter.Id,
                    config: DistributionStatisticConfig
                ): DistributionStatisticConfig {
                    return DistributionStatisticConfig.builder()
                        .percentilesHistogram(true)
                        .build()
                        .merge(config)
                }
            })

        vertx.deployVerticle<MainVerticle>(deploymentOptions)
        log.info("Application start")
    }

    suspend fun stop() {
        vertx.close().await()
        log.info("Application stop")
    }

    companion object {
        @JvmStatic
        val log = logger<Application>()

        @JvmStatic
        fun main(args: Array<String>) {
            try {
                val application = DaggerApplicationComponent
                    .create().application

                application.start()
                val shutdownManager = DefaultShutdownManager()
                shutdownManager.onShutdown { application.stop() }
            } catch (e: Exception) {
                log.error("Error in start Application", e)
            }
        }
    }
}