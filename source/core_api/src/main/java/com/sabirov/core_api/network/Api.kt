package com.sabirov.core_api.network

import com.sabirov.core_api.entities.auth.LogInBody
import com.sabirov.core_api.entities.auth.RegisterBody
import com.sabirov.core_api.entities.auth.User
import com.sabirov.core_api.entities.camping.Camping
import com.sabirov.core_api.entities.hike.Hike
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface Api {

    companion object {
        const val API_PATH = "api"
    }

    @POST("$API_PATH/register")
    suspend fun register(@Body registerBody: RegisterBody): ResponseBody

    @POST("$API_PATH/login")
    suspend fun login(@Body logInBody: LogInBody): User

    @GET("$API_PATH/profile/{profileId}")
    suspend fun getProfile(@Path("profileId") profileId: Number): User

    @PUT("$API_PATH/profile/{profileId}")
    suspend fun updateProfile(
        @Path("profileId") profileId: Number,
        @Body user: User
    ): ResponseBody

    @POST("$API_PATH/profile/{profileId}/avatar")
    suspend fun uploadPhoto(
        @Path("profileId") profileId: Number,
        @Body file: RequestBody
    ): ResponseBody

    @GET("$API_PATH/campings")
    suspend fun getCampings(): List<Camping>

    @GET("$API_PATH/camping/{campingId}")
    suspend fun getCampingById(
        @Path("campingId") campingId: Number
    ): Camping

    @GET("$API_PATH/hikes")
    suspend fun getHikes(): List<Hike>

    @GET("$API_PATH/hike/{hikeId}")
    suspend fun getHikeById(
        @Path("hikeId") campingId: Number
    ): Hike

}