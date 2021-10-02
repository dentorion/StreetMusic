// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlinVersion = "1.5.21"
    val gradleVersion = "7.1.0-alpha05"
    val hiltVersion = "2.38.1"
    val googleServicesVersion = "4.3.10"

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:$gradleVersion")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
        classpath ("com.google.gms:google-services:$googleServicesVersion")
        classpath ("com.google.firebase:firebase-crashlytics-gradle:2.7.1")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}