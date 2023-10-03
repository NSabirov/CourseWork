package com.sabirov.core_api.entities.auth

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("access_token")
    val token: String?,
    @SerializedName(value = "username", alternate = ["UserName"])
    val userName: String?,
    @SerializedName(value = "profileId", alternate= ["Id", "id"])
    val profileId: Number?,
    @SerializedName("PhotoLink")
    val photoUrl: String?,
    @SerializedName("FirstName")
    val firstName: String?,
    @SerializedName("LastName")
    val lastName: String?,
    @SerializedName("Patronymic")
    val patronymic: String?,
    @SerializedName("PhoneNumber")
    val phoneNumber: String?,
    @SerializedName("Email")
    val email: String?
)
