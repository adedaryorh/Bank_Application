package com.example.fintechapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fintechapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
     private lateinit var navView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupNavigation()

        setupBottomNavigation()


        appBarConfiguration = AppBarConfiguration(setOf())


    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the ActionBar with NavController
        setupActionBarWithNavController(navController)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)

        // Update toolbar title based on destination
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            binding.toolbar.title = destination.label
//        }
    }
    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    private fun setupBottomNavigation() {
        binding.bottomNav.setupWithNavController(navController)

        // Handle reselection to reset back stack
//        binding.bottomNav.setOnItemReselectedListener { menuItem ->
//            val reselectedDestinationId = menuItem.itemId
//            //  navController.popBackStack(reselectedDestinationId, inclusive = false)
//        }
    }

}


