package uz.dkamaloff.uzdroidbot.di

import dagger.Module
import dagger.Provides
import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import io.vertx.pgclient.PgConnectOptions
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.PoolOptions
import uz.dkamaloff.uzdroidbot.db.DatabaseInvoke
import uz.dkamaloff.uzdroidbot.db.DatabaseInvokeImpl
import javax.inject.Singleton


@Module
object DatabaseModule {

    @[Provides Singleton]
    fun providePgConnectOptions(
        deploymentOptions: DeploymentOptions,
    ): PgConnectOptions {
        val config = deploymentOptions
            .config
            .getJsonObject("POSTGRES")

        return PgConnectOptions()
            .setHost(config.getString("HOST"))
            .setUser(config.getString("USER"))
            .setPort(config.getInteger("PORT"))
            .setDatabase(config.getString("NAME"))
            .setPassword(config.getString("PASS"))
    }

    @[Provides Singleton]
    fun providePoolOptions(): PoolOptions = PoolOptions().setMaxSize(5)

    @[Provides Singleton]
    fun provideDatabase(pool: PgPool): DatabaseInvoke = DatabaseInvokeImpl(pool)

    @[Provides Singleton]
    fun providePgPool(
        vertx: Vertx,
        connectOptions: PgConnectOptions,
        poolOptions: PoolOptions
    ): PgPool = PgPool.pool(vertx, connectOptions, poolOptions)
}
