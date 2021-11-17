package com.entin.streetmusic.common.repository.implementation.remote

import com.entin.streetmusic.common.repository.interfaces.remote.RemoteSource
import javax.inject.Inject

class RemoteSource @Inject constructor(
    private val firebaseDb: FirebaseDb,
    private val googleCloudFunctions: GoogleCloudFunctions,
    private val apiTimeServer: ApiTimeServer,
) : RemoteSource {

    override fun firebaseDb() = firebaseDb

    override fun googleCloudFunctions() = googleCloudFunctions

    override fun apiTimeServer() = apiTimeServer
}