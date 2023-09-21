package com.sabirov.coursework

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.sabirov.coursework.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
            val navController = navHostFragment.navController
            navView.setupWithNavController(navController)
            navView.setOnItemSelectedListener {
                when (it.itemId) {
                    com.sabirov.home.R.id.camps_nav_graph -> navController.setGraph(com.sabirov.home.R.navigation.camps_navigation)
                    com.sabirov.temp.R.id.hike_nav_graph -> navController.setGraph(com.sabirov.temp.R.navigation.hike_navigation)
                }
                true
            }
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