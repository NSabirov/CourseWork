package com.sabirov

interface HikesNavigation {
    fun navigateToHikeInfo(hikeId: Number)

    fun onBackPressed(isVisible: Boolean)
}