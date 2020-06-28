package com.darrenfinch.mymealplanner.domain.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.darrenfinch.mymealplanner.R

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
