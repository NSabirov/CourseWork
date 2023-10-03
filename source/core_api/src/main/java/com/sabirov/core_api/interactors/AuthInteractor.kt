package com.sabirov.core_api.interactors

import com.sabirov.core_api.entities.auth.LogInBody
import com.sabirov.core_api.entities.auth.RegisterBody
import com.sabirov.core_api.entities.auth.User
import okhttp3.ResponseBody
import kotlinx.coroutines.flow.Flow

interface AuthInteractor {

    suspend fun register(registerBody: RegisterBody): Flow<ResponseBody>

    suspend fun logIn(logInBody: LogInBody): Flow<User>
}