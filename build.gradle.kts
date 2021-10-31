// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        jcenter() // Warning: this repository is going to shut down soon
    }
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}