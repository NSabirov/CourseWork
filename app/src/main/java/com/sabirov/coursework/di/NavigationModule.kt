package com.sabirov.coursework.di

import com.example.core_api.CommonNavigation
import com.sabirov.AuthNavigation
import com.sabirov.CampingsNavigation
import com.sabirov.coursework.navigation.AuthNavImpl
import com.sabirov.coursework.navigation.CampingsNavImpl
import com.sabirov.coursework.navigation.CommonNavImpl
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
    abstract fun homeNavigation(homeNav: CampingsNavImpl): CampingsNavigation

    @Binds
    abstract fun commonNavigation(commonNav: CommonNavImpl): CommonNavigation
}