@file:Suppress("unused")

package dependencies

object Firebase {

    /**
     * Import the BoM for the Firebase platform
     *  implementation(platform((bom)))
     */

    const val bom = "com.google.firebase:firebase-bom:${Versions.bom}"

    /**
     * Declare the dependency for the Firebase Authentication library
     * When using the BoM, you don't specify versions in Firebase library dependencies
     * implementation(auth)
     */

    const val auth = "com.google.firebase:firebase-auth-ktx"

    /**
     *
     */

    const val firestoreKtx = "com.google.firebase:firebase-firestore-ktx:${Versions.firebaseKtx}"

    /**
     *
     */

    const val coroutinePlayServices = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.coroutines}"

    /**
     * Analytics
     */
    const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx:${Versions.crashlytics}"
    const val analytics = "com.google.firebase:firebase-analytics-ktx:${Versions.analytics}"

    /**
     * Storage
     */
    const val firebaseStorageKtx = "com.google.firebase:firebase-storage-ktx"

}