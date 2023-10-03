package com.sabirov.coursework.navigation

import com.sabirov.AuthNavigation
import javax.inject.Inject

class AuthNavImpl @Inject constructor(
    private val commonNavigation: CommonNavImpl
) : AuthNavigation {

    override fun navigateToMain() {
        commonNavigation.bottomNavVisible(true)
        commonNavigation.navController.navigate(com.sabirov.authorization.R.id.action_authFragment_to_campsFragment)
    }

    override fun navigateToAuth() {
        commonNavigation.bottomNavVisible(false)
        commonNavigation.navController
            .setGraph(com.sabirov.authorization.R.navigation.auth_navigation)
    }

    override fun navigateToLogIn() {
        commonNavigation.bottomNavVisible(false)
        commonNavigation.navController
            .navigate(com.sabirov.authorization.R.id.action_authorizationFragment_to_logInFragment)
    }

    override fun navigateToRegistration() {
        commonNavigation.navController
            .navigate(com.sabirov.authorization.R.id.action_authorizationFragment_to_registrationFragment)
    }

    override fun onBackPressed(isVisible: Boolean) {
        commonNavigation.onBackPressed(isVisible)
    }
}