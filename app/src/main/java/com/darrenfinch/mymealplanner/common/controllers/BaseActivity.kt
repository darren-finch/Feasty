package com.darrenfinch.mymealplanner.common.controllers

import androidx.fragment.app.FragmentActivity
import com.darrenfinch.mymealplanner.Feasty
import com.darrenfinch.mymealplanner.common.dependencyinjection.ActivityCompositionRoot

abstract class BaseActivity : FragmentActivity() {
    val activityCompositionRoot: ActivityCompositionRoot by lazy {
        ActivityCompositionRoot((application as Feasty).getCompositionRoot(), this)
    }
}