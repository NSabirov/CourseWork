package com.sabirov.coursework.di

import com.sabirov.coursework.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideBaseUrl(): String {
        return BuildConfig.BASE_URL
    }
}