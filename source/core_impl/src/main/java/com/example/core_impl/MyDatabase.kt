package com.example.core_impl

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core_api.database.DatabaseContract
import com.example.core_api.dto.Camping

@Database(entities = [Camping::class], version = 1)
abstract class MyDatabase : RoomDatabase(), DatabaseContract