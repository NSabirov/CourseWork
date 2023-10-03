package com.sabirov.coursework.navigation

import androidx.core.os.bundleOf
import com.sabirov.HikesNavigation
import com.sabirov.resources.BundleArguments.Companion.ARG_HIKE_ID
import javax.inject.Inject

class HikesNavImpl @Inject constructor(
    private val commonNavigation: CommonNavImpl
) : HikesNavigation {
    override fun navigateToHikeInfo(hikeId: Number) {
        commonNavigation.bottomNavVisible(false)
        commonNavigation.navController.navigate(
            com.sabirov.hikes.R.id.action_hikesFragment_to_hikeInfoFragment,
            bundleOf(
                ARG_HIKE_ID to hikeId
            )
        )
    }

    override fun onBackPressed(isVisible: Boolean) {
        commonNavigation.onBackPressed(isVisible)
    }
}