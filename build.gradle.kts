// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Gradle
        classpath("com.android.tools.build:gradle:${Android.gradleVersion}")
        // Kotlin
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Android.kotlinVersion}")
        // Hilt
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Android.hiltVersion}")
        // GMS
        classpath("com.google.gms:google-services:${Android.googleServicesVersion}")
        // Secrets
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:${Android.secret}")
        // Firebase
        classpath("com.google.firebase:firebase-crashlytics-gradle:${Android.crashlytics}")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}