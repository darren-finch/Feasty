package com.darrenfinch.mymealplanner.common.controllers

import androidx.fragment.app.FragmentActivity
import com.darrenfinch.mymealplanner.MyMealPlanner
import com.darrenfinch.mymealplanner.common.dependencyinjection.ActivityCompositionRoot
import com.darrenfinch.mymealplanner.common.dependencyinjection.FragmentCompositionRoot

open class BaseActivity : FragmentActivity() {
    val activityCompositionRoot: ActivityCompositionRoot by lazy {
        ActivityCompositionRoot((application as MyMealPlanner).getCompositionRoot(), this)
    }
}