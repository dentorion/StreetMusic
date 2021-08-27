@file:Suppress("unused")

package dependencies

object Base {
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val composeUi = "androidx.compose.ui:ui:${Versions.composeUi}"
    const val composeMaterial = "androidx.compose.material:material:${Versions.composeMaterial}"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.composeUiTooling}"
    const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val composeActivity = "androidx.activity:activity-compose:${Versions.composeActivity}"

    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val jUnitTest = "androidx.test.ext:junit:${Versions.jUnitTest}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val jUnitTestCompose = "androidx.compose.ui:ui-test-junit4:${Versions.jUnitTestCompose}"

}