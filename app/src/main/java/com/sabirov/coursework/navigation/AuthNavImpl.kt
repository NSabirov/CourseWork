package com.sabirov.coursework.navigation

import androidx.core.net.toUri
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.sabirov.AuthNavigation
import com.sabirov.coursework.MainActivity
import com.sabirov.coursework.R
import javax.inject.Inject

class AuthNavImpl @Inject constructor(
    private val activity: FragmentActivity
) : AuthNavigation {

    private val navHost: NavHostFragment =
        activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
    private val navController = navHost.navController

    override fun navigateToMain() {
        (activity as? MainActivity)?.bottomNavVisible(true)
        val request = NavDeepLinkRequest.Builder
            .fromUri("myapp://ex.my.app/home_fragment".toUri())
            .build()
        navController.navigate(request, NavOptions.Builder().setPopUpTo(com.sabirov.authorization.R.id.authorizationFragment, true).build())
    }

    override fun navigateToAuth() {
        (activity as? MainActivity)?.bottomNavVisible(false)
        val request = NavDeepLinkRequest.Builder
            .fromUri("myapp://ex.my.app/auth_fragment".toUri())
            .build()
        navController.navigate(request)
    }

    override fun navigateToVerification() {
        (activity as? MainActivity)?.bottomNavVisible(false)
        navController.navigate(com.sabirov.authorization.R.id.action_authorizationFragment_to_verificationFragment)
    }

}