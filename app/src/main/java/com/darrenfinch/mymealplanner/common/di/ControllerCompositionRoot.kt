package com.darrenfinch.mymealplanner.common.di

import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.darrenfinch.mymealplanner.common.views.ViewMvcFactory
import com.darrenfinch.mymealplanner.model.FoodsRepository

//This composition root is scoped to a controller - e.g fragments or possibly an entire activity
class ControllerCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {
    private fun getActivity(): FragmentActivity {
        return activityCompositionRoot.getActivity()
    }

    private fun getContext(): Context? {
        return getActivity()
    }

    private fun getLayoutInflater(): LayoutInflater {
        return LayoutInflater.from(getContext())
    }

    fun getViewMvcFactory(): ViewMvcFactory {
        return ViewMvcFactory(getLayoutInflater())
    }

    fun getFoodsRepository() = activityCompositionRoot.getFoodsRepository()
}