package com.example.core_api.database

interface DatabaseContract {

    fun campingsDao(): CampingsDao

    fun hikedDao(): HikesDao
}