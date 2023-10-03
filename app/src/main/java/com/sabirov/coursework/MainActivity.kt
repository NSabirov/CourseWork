package com.sabirov.coursework

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.sabirov.core_impl.SessionKeeper
import com.sabirov.coursework.databinding.ActivityMainBinding
import com.sabirov.resources.BundleArguments.Companion.ARG_BOTTOM_VIEW_VISIBILITY
import com.sabirov.resources.BundleArguments.Companion.ARG_IS_VISIBLE
import com.sabirov.resources.BundleArguments.Companion.ARG_NAV_STATE
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var sessionKeeper: SessionKeeper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as? NavHostFragment
        val navController = navHostFragment?.navController
        navController?.let {
            if (savedInstanceState?.containsKey(ARG_NAV_STATE) == true) {
                navController.restoreState(savedInstanceState.getBundle(ARG_NAV_STATE))
            }
            binding.apply {

                navView.setupWithNavController(it)
                /*navView.setOnItemSelectedListener { item ->
                    when (item.itemId) {
                        com.sabirov.campings.R.id.camps_nav_graph -> {
                            it.setGraph(com.sabirov.campings.R.navigation.camps_navigation)
                        }

                       *//* com.sabirov.hikes.R.id.hike_nav_graph -> {
                            it.setGraph(com.sabirov.hikes.R.navigation.hike_navigation)
                        }*//*
                    }
                    true
                }*/
            }
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putBundle(
            ARG_NAV_STATE,
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as? NavHostFragment)?.navController?.saveState()
        )
        savedInstanceState.putBundle(
            ARG_BOTTOM_VIEW_VISIBILITY,
            bundleOf(
                ARG_IS_VISIBLE to binding.navView.isVisible
            )
        )
    }

    // restore in RestoreInstanceState
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val bundle = savedInstanceState.getBundle(ARG_NAV_STATE)
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as? NavHostFragment)?.navController?.restoreState(
            bundle
        )
        savedInstanceState.getBundle(ARG_BOTTOM_VIEW_VISIBILITY)?.getBoolean(ARG_IS_VISIBLE)?.let {
            bottomNavVisible(it)
        }
    }

    fun bottomNavVisible(visible: Boolean) {
        binding.navView.visibility = if (visible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}