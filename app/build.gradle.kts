plugins {
    id("com.android.application")
    id("kotlin-android")

    // Hilt
    kotlin("kapt")
    id("dagger.hilt.android.plugin")

}

android {
    compileSdk = Android.compileSdk
    buildToolsVersion = Android.buildTools

    defaultConfig {
        applicationId = "com.example.streetmusic2"
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.0-beta09"
    }
}

dependencies {
    Dependencies.base.apply {
        implementation (coreKtx)
        implementation (appcompat)
        implementation (material)
        implementation (composeUi)
        implementation (composeMaterial)
        implementation (composeUiTooling)
        implementation (lifecycle)
        implementation (composeActivity)

        testImplementation (jUnit)
        androidTestImplementation (jUnitTest)
        androidTestImplementation (espresso)
        androidTestImplementation (jUnitTestCompose)
    }

    Dependencies.hilt.apply {
        implementation(main)
        kapt(compile)
        implementation(navigation)
    }

    Dependencies.retrofit.apply {
        implementation(gson)
    }
}

kapt {
    correctErrorTypes = true
}