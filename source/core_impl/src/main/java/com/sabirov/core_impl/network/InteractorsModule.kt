package com.sabirov.core_impl.network

import com.sabirov.core_api.interactors.AuthInteractor
import com.sabirov.core_api.interactors.CampingsInteractor
import com.sabirov.core_api.interactors.CommonInteractor
import com.sabirov.core_api.interactors.HikesInteractor
import com.sabirov.core_api.network.Api
import com.sabirov.core_impl.interactors_impl.AuthInteractorImpl
import com.sabirov.core_impl.interactors_impl.CampingsInteractorImpl
import com.sabirov.core_impl.interactors_impl.CommonInteractorImpl
import com.sabirov.core_impl.interactors_impl.HikesInteractorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class InteractorsModule {
    @Provides
    @Singleton
    fun provideAuthInteractor(
        api: Api
    ): AuthInteractor = AuthInteractorImpl(api)

    @Provides
    @Singleton
    fun provideCampingsInteractor(
        api: Api
    ): CampingsInteractor = CampingsInteractorImpl(api)

    @Provides
    @Singleton
    fun provideHikesInteractor(
        api: Api
    ): HikesInteractor = HikesInteractorImpl(api)

    @Provides
    @Singleton
    fun provideCommonInteractor(
        api: Api
    ): CommonInteractor = CommonInteractorImpl(api)
}