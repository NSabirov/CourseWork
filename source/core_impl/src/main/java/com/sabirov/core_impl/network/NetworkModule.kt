package com.sabirov.core_impl.network

import android.annotation.SuppressLint
import com.sabirov.core_api.network.Api
import com.sabirov.core_impl.BuildConfig
import com.sabirov.core_impl.SessionKeeper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder =
        OkHttpClient.Builder().apply {
            /*val tlsSpecs: List<ConnectionSpec> = listOf(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS)
            connectionSpecs(tlsSpecs)*/
            if (BuildConfig.DEBUG) {
                val httpLogger = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                connectTimeout(3, TimeUnit.MINUTES)
                writeTimeout(3, TimeUnit.MINUTES)
                readTimeout(3, TimeUnit.MINUTES)
                addNetworkInterceptor(httpLogger)
            } else {
                connectTimeout(3, TimeUnit.MINUTES)
                writeTimeout(3, TimeUnit.MINUTES)
                readTimeout(3, TimeUnit.MINUTES)
                connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
            }
            cookieJar(SessionCookieJar())
        }

    private class SessionCookieJar : CookieJar {
        private var cookies: List<Cookie> = arrayListOf()

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            this.cookies = ArrayList(cookies)
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            return cookies
        }
    }


    @Provides
    @Singleton
    @SuppressLint("CustomX509TrustManager", "TrustAllX509TrustManager")
    fun provideOkHttpClient(
        okHttpClientBuilder: OkHttpClient.Builder,
        sessionKeeper: SessionKeeper,
        languageManager: LanguageManager,
    ): OkHttpClient =
        with(okHttpClientBuilder) {
            return try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts =
                    arrayOf<TrustManager>(
                        object : X509TrustManager {
                            @Throws(CertificateException::class)
                            override fun checkClientTrusted(
                                chain: Array<X509Certificate>,
                                authType: String
                            ) {
                            }

                            @Throws(CertificateException::class)
                            override fun checkServerTrusted(
                                chain: Array<X509Certificate>,
                                authType: String
                            ) {
                            }

                            override fun getAcceptedIssuers(): Array<X509Certificate> {
                                return arrayOf()
                            }
                        }
                    )

                // Install the all-trusting trust manager
                val sslContext =
                    SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, SecureRandom())

                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
                sslSocketFactory(
                    sslSocketFactory,
                    (trustAllCerts[0] as X509TrustManager)
                )
                hostnameVerifier { _, _ -> true }
                addNetworkInterceptor(AuthHeaderInterceptor(sessionKeeper, languageManager))
                if (BuildConfig.DEBUG) {
                    val httpLogger = HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                    addNetworkInterceptor(httpLogger)
                }
                build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

    @Provides
    @Singleton
    fun provideApi(
        okHttpClient: OkHttpClient,
        gson: Gson,
        baseUrl: String
    ): Api {
        val api = with(Retrofit.Builder()) {
            addConverterFactory(GsonConverterFactory.create(gson))
            client(okHttpClient)
            baseUrl(baseUrl)
            build()
        }.create(Api::class.java)
        return api
    }


    @Provides
    @Singleton
    fun provideGson(): Gson = with(GsonBuilder()) {
        serializeNulls()
        registerTypeAdapter(Date::class.java, GsonUTCDateAdapter())
        create()
    }
}