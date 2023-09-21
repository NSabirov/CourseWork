package com.example.core_api.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CAMPINGS")
data class Camping(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String
)
