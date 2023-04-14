package com.sabirov.coursework.navigation

import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import com.sabirov.HomeNavigation
import com.sabirov.coursework.MainActivity
import com.sabirov.coursework.R
import javax.inject.Inject

class HomeNavImpl @Inject constructor(
    private val activity: FragmentActivity
) : HomeNavigation {
    private val navHost: NavHostFragment =
        activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
    private val navController = navHost.navController

    override fun navigateToCreate() {
        navController.navigate(com.sabirov.home.R.id.action_homeFragment_to_createFragment)
    }

    override fun bottomNavViewVisible(visible: Boolean) {
        (activity as? MainActivity)?.bottomNavVisible(visible)
    }
}