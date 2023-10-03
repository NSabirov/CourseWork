package com.sabirov.core_api.interactors

import com.sabirov.core_api.entities.auth.User
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import java.io.File

interface CommonInteractor {

    suspend fun getProfile(profileId: Number): Flow<User>

    suspend fun updateProfile(profileId: Number, user: User): Flow<ResponseBody>

    suspend fun uploadPhoto(profileId: Number, file: File): Flow<ResponseBody>
}