package com.sabirov.coursework.navigation

import com.sabirov.profile.ProfileNavigation
import javax.inject.Inject

class ProfileNavImpl @Inject constructor(
    private val commonNavigation: CommonNavImpl
) : ProfileNavigation {
    override fun logOut() {
        //commonNavigation.graph.setStartDestination(com.sabirov.authorization.R.id.auth_nav_graph)
        commonNavigation.navController.navigate(com.sabirov.profile.R.id.action_profileFragment_to_authFragment)
        //.setGraph(com.sabirov.authorization.R.navigation.auth_navigation)
    }

    override fun returnToCampings() {
        commonNavigation.bottomNavVisible(true)
        //commonNavigation.graph.setStartDestination(com.sabirov.campings.R.id.camps_nav_graph)
        commonNavigation.navController.setGraph(com.sabirov.campings.R.navigation.camps_navigation)
    }

    override fun onBackPressed(isVisible: Boolean) {
        commonNavigation.onBackPressed(isVisible)
    }

}