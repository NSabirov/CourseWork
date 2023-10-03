package com.sabirov.core_impl.interactors_impl

import com.sabirov.core_api.entities.auth.User
import com.sabirov.core_api.interactors.CommonInteractor
import com.sabirov.core_api.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import java.io.File

class CommonInteractorImpl(private val api: Api) : CommonInteractor {
    override suspend fun getProfile(profileId: Number): Flow<User> {
        return flow { emit(api.getProfile(profileId)) }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateProfile(profileId: Number, user: User): Flow<ResponseBody> {
        return flow { emit(api.updateProfile(profileId, user)) }.flowOn(Dispatchers.IO)
    }

    override suspend fun uploadPhoto(profileId: Number, file: File): Flow<ResponseBody> {
        val requestFile = file.asRequestBody("file".toMediaTypeOrNull())
        val body: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", file.name, requestFile)
            .build()
        return flow { emit(api.uploadPhoto(profileId, body)) }.flowOn(Dispatchers.IO)
    }
}