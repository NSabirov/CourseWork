package com.sabirov.core_impl

import com.sabirov.core_api.entities.auth.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionKeeper @Inject constructor(
    private val prefs: Prefs
) {

    var userAccount: User? = prefs.account
        private set

    val token: String?
        get() = userAccount?.token

    fun setUserAccount(userAccount: User?) {
        this.userAccount = userAccount
        prefs.account = userAccount
    }
}