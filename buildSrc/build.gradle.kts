plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    google()
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation(kotlin("gradle-plugin", version = Deps.Kotlin.kotlinVersion))
    implementation(kotlin("serialization", version = Deps.Kotlin.kotlinVersion))
}

kotlin {
    sourceSets.getByName("main").kotlin.srcDir("buildSrc/src/main/kotlin")
}