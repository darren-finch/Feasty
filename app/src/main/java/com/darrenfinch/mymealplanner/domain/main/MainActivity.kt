package com.darrenfinch.mymealplanner.domain.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.replace
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.controllers.BaseActivity
import com.ncapdevi.fragnav.FragNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val screensNavigator = activityCompositionRoot.getScreensNavigator()
        screensNavigator.init(savedInstanceState)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.tab1 -> {
                    screensNavigator.switchTab(FragNavController.TAB1)
                    true
                }
                R.id.tab2 -> {
                    screensNavigator.switchTab(FragNavController.TAB2)
                    true
                }
                R.id.tab3 -> {
                    screensNavigator.switchTab(FragNavController.TAB3)
                    true
                }
                else -> false
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        activityCompositionRoot.getScreensNavigator().onSaveInstanceState(outState)
    }
}
