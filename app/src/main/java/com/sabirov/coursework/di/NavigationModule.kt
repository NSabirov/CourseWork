package com.sabirov.coursework.di

import com.sabirov.core_api.CommonNavigation
import com.sabirov.AuthNavigation
import com.sabirov.CampingsNavigation
import com.sabirov.HikesNavigation
import com.sabirov.coursework.navigation.AuthNavImpl
import com.sabirov.coursework.navigation.CampingsNavImpl
import com.sabirov.coursework.navigation.CommonNavImpl
import com.sabirov.coursework.navigation.HikesNavImpl
import com.sabirov.coursework.navigation.ProfileNavImpl
import com.sabirov.profile.ProfileNavigation
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
    abstract fun profileNavigation(profileNav: ProfileNavImpl): ProfileNavigation

    @Binds
    abstract fun hikesNavigation(hikesNav: HikesNavImpl): HikesNavigation
    @Binds
    abstract fun commonNavigation(commonNav: CommonNavImpl): CommonNavigation
}