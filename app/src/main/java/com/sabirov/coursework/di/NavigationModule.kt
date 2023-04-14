package com.sabirov.coursework.di

import com.sabirov.AuthNavigation
import com.sabirov.HomeNavigation
import com.sabirov.coursework.navigation.AuthNavImpl
import com.sabirov.coursework.navigation.HomeNavImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class NavigationModule {
    @Binds
    abstract fun authNavigation(authNav: AuthNavImpl): AuthNavigation

    @Binds
    abstract fun homeNavigation(homeNav: HomeNavImpl): HomeNavigation
}