plugins {
    id("com.android.library")
    id("kotlin-android")

    // Hilt
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Android.compileSdk
    buildToolsVersion = Android.buildTools

    defaultConfig {
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
    }
}

dependencies {
    // Hilt
    Dependencies.hilt.apply {
        implementation(main)
        kapt(compile)
    }

    // Retrofit
    Dependencies.retrofit.apply {
        api(retrofit)
        api(gson)
        api(loggingInterceptor)
    }
}