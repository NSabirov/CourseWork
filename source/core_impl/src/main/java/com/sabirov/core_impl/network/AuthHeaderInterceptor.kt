package com.sabirov.core_impl.network

import com.sabirov.core_impl.SessionKeeper
import okhttp3.Interceptor
import okhttp3.Response

class AuthHeaderInterceptor(
    private val sessionKeeper: SessionKeeper,
    private val languageManager: LanguageManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val token = sessionKeeper.token
        if (token != null) {
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .addHeader(
                    "Accept-Language",
                    if (languageManager.getLanguage().toString()
                            .replace('_', '-') == "ru-RU"
                    ) "ru-RU"
                    else "en-EN"
                )
                .build()
        } else {
            request = request.newBuilder()
                .addHeader(
                    "Accept-Language",
                    if (languageManager.getLanguage().toString()
                            .replace('_', '-') == "ru-RU"
                    ) "ru-RU"
                    else "en-EN"
                )
                .build()
        }
        return chain.proceed(request)
    }
}