package com.sabirov.core_api.entities.camping

import com.google.gson.annotations.SerializedName

data class Camping(
    @SerializedName("Id")
    val id: Number?,
    @SerializedName("Name")
    val name: String?,
    @SerializedName("Description")
    val description: String?,
    @SerializedName("Place")
    val place: String?,
    @SerializedName("NearestTown")
    val nearestTown: String?,
    @SerializedName("WebSite")
    val site: String?,
    @SerializedName("Phone")
    val phone: String?,
    @SerializedName("CreatorUserProfileId")
    val creatorId: Number?,
    @SerializedName("Photos")
    val photos: List<String> = mutableListOf(),
    @SerializedName("CreatedDate")
    val createDate: String?,
    @SerializedName("UpdatedDate")
    val updateDate: String?
)
