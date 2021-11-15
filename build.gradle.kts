// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Android.gradleVersion}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Android.kotlinVersion}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Android.hiltVersion}")
        classpath("com.google.gms:google-services:${Android.googleServicesVersion}")
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.0")
        classpath("com.google.firebase:firebase-crashlytics-gradle:${Android.crashlytics}")
        classpath("com.android.tools.build:gradle:3.4.3")
        classpath("com.google.firebase:perf-plugin:1.4.0")

    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}