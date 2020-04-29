package com.darrenfinch.mymealplanner

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.container)
        navController.setGraph(R.navigation.nav_main)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setupWithNavController(navController)
        bottomNav.setOnNavigationItemSelectedListener{ item ->
            NavigationUI.onNavDestinationSelected(item, navController)
            true
        }
    }
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean
//    {
//        menuInflater.inflate(R.menu.options_menu, menu)
//        return true
//    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean
//    {
//        if(item.itemId == R.id.settingsFragment)
//            Navigation.findNavController(this, R.id.container).navigate(R.id.settingsFragment)
//        return true
//    }
}
