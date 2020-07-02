package com.darrenfinch.mymealplanner.common.dependencyinjection

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import com.darrenfinch.mymealplanner.domain.addeditfood.controller.AddEditFoodController
import com.darrenfinch.mymealplanner.domain.addeditfood.controller.AddEditFoodViewModel

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

    fun getApplication() : Application {
        return activityCompositionRoot.getApplication()
    }

    fun getViewMvcFactory(): ViewMvcFactory {
        return ViewMvcFactory(getLayoutInflater())
    }

    fun getFoodsRepository() = activityCompositionRoot.getFoodsRepository()
    fun getMealsRepository() = activityCompositionRoot.getMealsRepository()

    fun getAddEditFoodController(viewModel: AddEditFoodViewModel) = AddEditFoodController(getFoodsRepository(), viewModel)
}