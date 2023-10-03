package com.sabirov.core_api.entities.hike

import com.google.gson.annotations.SerializedName
import com.sabirov.core_api.entities.auth.User

data class Hike(
    @SerializedName("Creators")
    val creators: List<User> = mutableListOf(),
    @SerializedName("UserProfiles")
    val users: List<User> = mutableListOf(),
    @SerializedName("Category")
    val category: Default?,
    @SerializedName("Landscape")
    val landscape: Default?,
    @SerializedName("Status")
    val status: Default?,
    @SerializedName("Description")
    val description: String?,
    @SerializedName("Date")
    val date: String?,
    @SerializedName("DateEnd")
    val dateEnd: String?,
    @SerializedName("Place")
    val place: String?,
    @SerializedName("Price")
    val price: String?,
    @SerializedName("CampingId")
    val campingId: Number?,
    @SerializedName("CategoryId")
    val categoryId: Number?,
    @SerializedName("LandscapeId")
    val landscapeId: Number?,
    @SerializedName("StatusId")
    val statusId: Number?,
    @SerializedName("CreatorUserProfileId")
    val creatorId: Number?,
    @SerializedName("Name")
    val name: String?,
    @SerializedName("Id")
    val id: Number?,
    @SerializedName("CreatedDate")
    val createDate: String?,
    @SerializedName("UpdatedDate")
    val updateDate: String?
)

data class Default(
    @SerializedName("Name")
    val name: String?,
    @SerializedName("Id")
    val id: Number?,
    @SerializedName("CreatedDate")
    val createDate: String?,
    @SerializedName("UpdatedDate")
    val updateDate: String?
)


