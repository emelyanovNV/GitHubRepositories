package ru.nemelianov.githubrepositories.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.ac_main.*
import ru.nemelianov.githubrepositories.R

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_GitHubRepositories)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_main)
        setSupportActionBar(toolbar)
        navController = findNavController(R.id.nav_host)
        appBarConfiguration = AppBarConfiguration.Builder().build()
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_list_repo ->
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                else ->
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }
    }

    override fun onSupportNavigateUp() = NavigationUI.navigateUp(navController, appBarConfiguration)
            || super.onSupportNavigateUp()
}