package com.entin.streetmusic.common.repository.interfaces.remote

interface RemoteSource {

    fun firebaseDb(): FirebaseDb

    fun googleCloudFunctions(): GoogleCloudFunctions

    fun apiTimeServer(): ApiTimeServer
}