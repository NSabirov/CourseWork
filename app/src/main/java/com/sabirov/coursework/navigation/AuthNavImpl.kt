package com.sabirov.coursework.navigation

import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.core_api.CommonNavigation
import com.sabirov.AuthNavigation
import com.sabirov.coursework.MainActivity
import com.sabirov.coursework.R
import javax.inject.Inject

class AuthNavImpl @Inject constructor(
    private val commonNavigation: CommonNavImpl
) : AuthNavigation {

    override fun navigateToMain() {
        commonNavigation.bottomNavVisible(true)
        commonNavigation.getController().setGraph(com.sabirov.home.R.navigation.camps_navigation)
    }

    override fun navigateToAuth() {
        commonNavigation.bottomNavVisible(false)
        commonNavigation.getController()
            .setGraph(com.sabirov.authorization.R.navigation.auth_navigation)
    }

    override fun navigateToLogIn() {
        commonNavigation.bottomNavVisible(false)
        commonNavigation.getController()
            .navigate(com.sabirov.authorization.R.id.action_authorizationFragment_to_logInFragment)
    }

    override fun navigateToRegistration() {
        commonNavigation.getController()
            .navigate(com.sabirov.authorization.R.id.action_authorizationFragment_to_registrationFragment)
    }

    override fun onBackPressed() {
        commonNavigation.onBackPressed()
    }
}