package com.sabirov.core_api.entities.auth

import com.google.gson.annotations.SerializedName

data class RegisterBody(
    @SerializedName("Email")
    val email: String,
    @SerializedName("Password")
    val password: String,
    @SerializedName("ConfirmationPassword")
    val confirmPassword: String
)
