package com.sabirov.profile

interface ProfileNavigation {
    fun logOut()

    fun returnToCampings()

    fun onBackPressed(isVisible: Boolean)
}