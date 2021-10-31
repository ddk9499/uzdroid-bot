import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("vertx-setup")
    id("kotlin-dagger-setup")
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "uz.dkamaloff"
version = "0.0.1"


dependencies {
    implementation(Deps.Kotlin.coroutines)
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("io.github.kotlin-telegram-bot.kotlin-telegram-bot:telegram:6.0.4")
}



tasks.test {
    useJUnit()
}

val shadowJar: ShadowJar by tasks
shadowJar.apply {
    manifest.attributes.apply {
        put("Implementation-Version", "1.0.0")
        put("Main-Class", "uz.dkamaloff.uzdroidbot.Application")
    }

    baseName = project.name + "-all"
}
tasks {
    "build" {
        dependsOn(shadowJar)
    }
}