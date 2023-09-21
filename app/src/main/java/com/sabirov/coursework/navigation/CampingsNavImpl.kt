package com.sabirov.coursework.navigation

import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.core_api.CommonNavigation
import com.sabirov.CampingsNavigation
import com.sabirov.coursework.MainActivity
import com.sabirov.coursework.R
import javax.inject.Inject

class CampingsNavImpl @Inject constructor(
    private val commonNavigation: CommonNavImpl
) : CampingsNavigation {
    override fun navigateToCampInfo() {
        commonNavigation.getController()
            .navigate(com.sabirov.home.R.id.action_campsFragment_to_campInfoFragment)
    }
}