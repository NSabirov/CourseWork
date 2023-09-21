package com.example.core_impl

import android.content.Context
import androidx.room.Room
import com.example.core_api.database.CampingsDao
import com.example.core_api.database.DatabaseContract
import com.example.core_api.database.HikesDao
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


private const val DATABASE_NAME = "CAMPS_DB"

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Reusable
    fun provideCampingsDao(campingsDatabaseContract: DatabaseContract): CampingsDao = campingsDatabaseContract.campingsDao()

    @Provides
    @Reusable
    fun provideHikesDao(campingsDatabaseContract: DatabaseContract): HikesDao = campingsDatabaseContract.hikedDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DatabaseContract {
        return Room.databaseBuilder(
            context,
            MyDatabase::class.java, DATABASE_NAME
        ).build()
    }
}