package com.entin.streetmusic.core.repository.interfaces.remote

interface RemoteSource {

    fun firebaseDb(): FirebaseDb

    fun googleCloudFunctions(): GoogleCloudFunctions

    fun apiTimeServer(): ApiTimeServer
}