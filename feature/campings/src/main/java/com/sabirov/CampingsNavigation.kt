package com.sabirov

interface CampingsNavigation {
    fun navigateToCampInfo(campId: Number)

    fun navigateToProfile(userId: Number)

    fun onBackPressed(isVisible: Boolean)
}