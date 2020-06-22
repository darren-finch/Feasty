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
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        val navController = Navigation.findNavController(this, R.id.navHostFragment)
//        bottomNavigationView.setupWithNavController(navController)
//        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
//            NavigationUI.onNavDestinationSelected(item, navController)
//            true
//        }
    }
}
