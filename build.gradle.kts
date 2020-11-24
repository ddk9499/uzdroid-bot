import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
}
group = "uz.dkamaloff"
version = "0.0.1"

repositories {
    jcenter()
    maven(url = "https://jitpack.io")
    maven { url = uri("https://dl.bintray.com/heapy/heap-dev") }
}

dependencies {
    val komodoVersion = "0.0.2-development-75"
    implementation("io.heapy.komodo:komodo-config-dotenv:$komodoVersion") {
        exclude("org.jetbrains.kotlinx")
    }

    implementation("io.github.kotlin-telegram-bot.kotlin-telegram-bot:telegram:6.0.0")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}