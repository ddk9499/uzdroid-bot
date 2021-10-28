import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(Deps.Kotlin.jdk8)
    implementation(Deps.Kotlin.reflect)
    implementation(Deps.Kotlin.serializationJson)

    testImplementation(kotlin("test"))
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

