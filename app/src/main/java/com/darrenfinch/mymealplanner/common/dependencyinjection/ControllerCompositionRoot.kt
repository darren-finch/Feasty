package com.darrenfinch.mymealplanner.common.dependencyinjection

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import com.darrenfinch.mymealplanner.domain.addeditfood.controller.AddEditFoodController
import com.darrenfinch.mymealplanner.domain.addeditfood.controller.AddEditFoodViewModel
import com.darrenfinch.mymealplanner.domain.allfoods.controller.AllFoodsController
import com.darrenfinch.mymealplanner.domain.allmeals.controller.AllMealsController
import com.darrenfinch.mymealplanner.domain.usecases.*

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

    private fun getFoodsRepository() = activityCompositionRoot.getFoodsRepository()
    private fun getMealsRepository() = activityCompositionRoot.getMealsRepository()

    private fun getGetAllFoodsUseCase() = GetAllFoodsUseCase(getFoodsRepository())
    private fun getGetSingleFoodUseCase() = GetSingleFoodUseCase(getFoodsRepository())
    private fun getInsertFoodUseCase() = InsertFoodUseCase(getFoodsRepository())
    private fun getUpdateFoodUseCase() = UpdateFoodUseCase(getFoodsRepository())
    private fun getDeleteFoodUseCase() = DeleteFoodUseCase(getFoodsRepository())
    private fun getGetAllMealsUseCase() = GetAllMealsUseCase(getMealsRepository())

    fun getAddEditFoodController(viewModel: AddEditFoodViewModel) = AddEditFoodController(getGetSingleFoodUseCase(), getInsertFoodUseCase(), getUpdateFoodUseCase(), viewModel)
    fun getAllFoodsController() = AllFoodsController(getGetAllFoodsUseCase(), getDeleteFoodUseCase())
    fun getAllMealsController() = AllMealsController(getGetAllMealsUseCase())
}