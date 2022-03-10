package com.example.listgames

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    companion object {
        var gameID = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.appNavHostFragment) as NavHostFragment? ?: return
        val navController = host.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        val bottomBar = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomBar.setupWithNavController(navController)
        toolBar.setupWithNavController(navController, appBarConfiguration)
    }
}