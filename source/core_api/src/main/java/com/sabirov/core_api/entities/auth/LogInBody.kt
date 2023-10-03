package com.sabirov.core_api.entities.auth

import com.google.gson.annotations.SerializedName

data class LogInBody(
    @SerializedName("UserName")
    val userName: String,
    @SerializedName("Password")
    val password: String
)
