package com.sabirov.coursework.navigation

import androidx.core.os.bundleOf
import com.sabirov.CampingsNavigation
import com.sabirov.coursework.R
import com.sabirov.resources.BundleArguments.Companion.ARG_CAMP_ID
import javax.inject.Inject
import com.sabirov.resources.BundleArguments.Companion.ARG_USER_ID

class CampingsNavImpl @Inject constructor(
    private val commonNavigation: CommonNavImpl
) : CampingsNavigation {
    override fun navigateToCampInfo(campId: Number) {
        commonNavigation.bottomNavVisible(false)
        commonNavigation.navController
            .navigate(
                com.sabirov.campings.R.id.action_campsFragment_to_campInfoFragment,
                bundleOf(
                    ARG_CAMP_ID to campId
                )
            )
    }

    override fun navigateToProfile(userId: Number) {
        commonNavigation.bottomNavVisible(false)
        commonNavigation.navController.navigate(
            com.sabirov.campings.R.id.action_campsFragment_to_profileFragment,
            bundleOf(
                ARG_USER_ID to userId
            )
        )
    }

    override fun onBackPressed(isVisible: Boolean) {
        commonNavigation.onBackPressed(isVisible)
    }
}