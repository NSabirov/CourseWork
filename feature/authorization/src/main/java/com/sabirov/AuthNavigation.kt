package com.sabirov

interface AuthNavigation {
    fun navigateToMain()

    fun navigateToAuth()

    fun navigateToLogIn()

    fun navigateToRegistration()
    fun onBackPressed()
}