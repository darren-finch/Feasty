package com.darrenfinch.mymealplanner

import android.app.Application
import com.darrenfinch.mymealplanner.common.di.CompositionRoot

class MyMealPlanner : Application() {
    private lateinit var compositionRoot: CompositionRoot

    override fun onCreate() {
        super.onCreate()
        compositionRoot = CompositionRoot(this)
    }

    fun getCompositionRoot() = compositionRoot
}