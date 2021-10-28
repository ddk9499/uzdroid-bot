import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}


dependencies {
    with(Deps.Vertx) {
        implementation(vertxWeb)
        implementation(vertxCore)
        implementation(vertxConfig)
        implementation(vertxKotlin)
        implementation(vertxMetrics)
        implementation(vertxPgClient)
        implementation(vertxHazelcast)
        implementation(vertxWebClient)
        implementation(vertxCoroutines)
    }

    implementation(Deps.Logback.logbackClassic)
    implementation(Deps.MicrometerIO.prometheus)

    testImplementation("io.vertx:vertx-unit:${Deps.Vertx.vertxVersion}")
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = commonCompilerArgs + jvmCompilerArgs
    }
}