object Deps {

    object Kotlin {
        const val kotlinVersion = "1.5.31"
        const val kotlinSerialization = "1.3.0"

        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
        const val jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2"
        const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerialization"
    }

    object Google {
        const val daggerVersion = "2.38.1"
        const val dagger = "com.google.dagger:dagger:$daggerVersion"
        const val daggerCompiler = "com.google.dagger:dagger-compiler:$daggerVersion"
    }

    object Vertx {
        const val vertxVersion = "4.1.4"
        const val vertxWeb = "io.vertx:vertx-web:$vertxVersion"
        const val vertxCore = "io.vertx:vertx-core:$vertxVersion"
        const val vertxConfig = "io.vertx:vertx-config:$vertxVersion"
        const val vertxPgClient = "io.vertx:vertx-pg-client:$vertxVersion"
        const val vertxKotlin = "io.vertx:vertx-lang-kotlin:$vertxVersion"
        const val vertxHazelcast = "io.vertx:vertx-hazelcast:$vertxVersion"
        const val vertxWebClient = "io.vertx:vertx-web-client:$vertxVersion"
        const val vertxMetrics = "io.vertx:vertx-micrometer-metrics:$vertxVersion"
        const val vertxCoroutines = "io.vertx:vertx-lang-kotlin-coroutines:$vertxVersion"
    }

    object MicrometerIO {
        const val prometheus = "io.micrometer:micrometer-registry-prometheus:1.7.3"
    }

    object Logback {
        const val logbackClassic = "ch.qos.logback:logback-classic:1.2.6"
    }
}