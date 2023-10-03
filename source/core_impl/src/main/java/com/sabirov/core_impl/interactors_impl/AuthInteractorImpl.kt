package com.sabirov.core_impl.interactors_impl

import com.sabirov.core_api.entities.auth.LogInBody
import com.sabirov.core_api.entities.auth.RegisterBody
import com.sabirov.core_api.entities.auth.User
import com.sabirov.core_api.interactors.AuthInteractor
import com.sabirov.core_api.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody

class AuthInteractorImpl(private val api: Api) : AuthInteractor {

    override suspend fun register(registerBody: RegisterBody): Flow<ResponseBody> {
        return flow { emit(api.register(registerBody)) }.flowOn(Dispatchers.IO)
    }

    override suspend fun logIn(logInBody: LogInBody): Flow<User> {
        return flow { emit(api.login(logInBody)) }.flowOn(Dispatchers.IO)
    }


}