plugins {
    id("vertx-setup")
    id("kotlin-dagger-setup")
}

group = "uz.dkamaloff"
version = "0.0.1"

repositories {
    jcenter()
    maven(url = "https://jitpack.io")
    maven { url = uri("https://dl.bintray.com/heapy/heap-dev") }
}

dependencies {
    implementation(Deps.Kotlin.coroutines)
    implementation("io.netty:netty-resolver-dns-native-macos:4.1.59.Final:osx-x86_64")
    implementation("io.github.kotlin-telegram-bot.kotlin-telegram-bot:telegram:6.0.4")
}