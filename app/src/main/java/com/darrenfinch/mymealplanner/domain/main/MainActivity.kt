package com.darrenfinch.mymealplanner.domain.main

import android.os.Bundle
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.controllers.BaseActivity
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.BackPressListener
import com.ncapdevi.fragnav.FragNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), BackPressDispatcher {

    private val backPressedListeners = hashSetOf<BackPressListener>()

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

    override fun registerListener(listener: BackPressListener) {
        backPressedListeners.add(listener)
    }

    override fun unregisterListener(listener: BackPressListener) {
        backPressedListeners.remove(listener)
    }

    override fun onBackPressed() {
        var backPressWasConsumed = false
        for (listener in backPressedListeners) {
            if(listener.onBackPressed()) {
                backPressWasConsumed = true
            }
        }
        if(!backPressWasConsumed) {
            super.onBackPressed()
        }
    }
}
