package com.sabirov.coursework.navigation

import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import com.sabirov.core_api.CommonNavigation
import com.sabirov.coursework.MainActivity
import com.sabirov.coursework.R
import dagger.hilt.EntryPoint
import javax.inject.Inject

class CommonNavImpl @Inject constructor(
    private val activity: FragmentActivity
) : CommonNavigation {
    private val navHost: NavHostFragment =
        activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
    val navController
        get() = navHost.navController

    override fun onBackPressed(isVisible: Boolean) {
        bottomNavVisible(isVisible)
        navController.popBackStack()
    }

    override fun bottomNavVisible(isVisible: Boolean) {
        (activity as? MainActivity)?.bottomNavVisible(isVisible)
    }
}