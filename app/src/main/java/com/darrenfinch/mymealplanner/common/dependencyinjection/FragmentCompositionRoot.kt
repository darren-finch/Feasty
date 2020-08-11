package com.darrenfinch.mymealplanner.common.dependencyinjection

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import com.darrenfinch.mymealplanner.common.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.addeditfood.controller.AddEditFoodController
import com.darrenfinch.mymealplanner.domain.addeditfood.controller.AddEditFoodViewModel
import com.darrenfinch.mymealplanner.domain.addeditmeal.controller.AddEditMealController
import com.darrenfinch.mymealplanner.domain.addeditmeal.controller.AddEditMealViewModel
import com.darrenfinch.mymealplanner.domain.allfoods.controller.AllFoodsController
import com.darrenfinch.mymealplanner.domain.allmeals.controller.AllMealsController
import com.darrenfinch.mymealplanner.domain.selectfoodformeal.controller.SelectFoodForMealController
import com.darrenfinch.mymealplanner.domain.selectmealfoodquantity.controller.SelectMealFoodQuantityController
import com.darrenfinch.mymealplanner.domain.usecases.*
import com.darrenfinch.mymealplanner.model.data.Meal
import com.darrenfinch.mymealplanner.model.data.MealFood

//This composition root is scoped to a fragment, which is a controller
class FragmentCompositionRoot(
    private val androidComponentsConfig: AndroidComponentsConfig,
    private val activityCompositionRoot: ActivityCompositionRoot
) {

    private fun getActivity(): FragmentActivity {
        return activityCompositionRoot.getActivity()
    }

    private fun getContext(): Context {
        return getActivity()
    }

    private fun getLayoutInflater(): LayoutInflater {
        return LayoutInflater.from(getContext())
    }

    fun getApplication(): Application {
        return activityCompositionRoot.getApplication()
    }

    fun getViewMvcFactory(): ViewMvcFactory {
        return ViewMvcFactory(getLayoutInflater())
    }

    private fun getFoodsRepository() = activityCompositionRoot.getFoodsRepository()
    private fun getMealsRepository() = activityCompositionRoot.getMealsRepository()

    private fun getGetAllFoodsUseCase() = GetAllFoodsUseCase(getFoodsRepository())
    private fun getGetFoodUseCase() = GetFoodUseCase(getFoodsRepository())
    private fun getInsertFoodUseCase() = InsertFoodUseCase(getFoodsRepository())
    private fun getUpdateFoodUseCase() = UpdateFoodUseCase(getFoodsRepository())
    private fun getDeleteFoodUseCase() = DeleteFoodUseCase(getFoodsRepository())
    private fun getInsertMealUseCase() = InsertMealUseCase(getMealsRepository())
    private fun getGetAllMealsUseCase() = GetAllMealsUseCase(getMealsRepository())

    //TODO: TEMP
    fun getDeleteAllMealsUseCase() = DeleteAllMealsUseCase(getMealsRepository())

    fun getAddEditFoodController(viewModel: AddEditFoodViewModel) = AddEditFoodController(
        getScreensNavigator(),
        getGetFoodUseCase(),
        getInsertFoodUseCase(),
        getUpdateFoodUseCase(),
        viewModel
    )

    fun getSelectMealFoodQuantityController(foodId: Int, currentMeal: Meal) =
        SelectMealFoodQuantityController(
            getScreensNavigator(),
            getGetFoodUseCase(),
            foodId,
            currentMeal
        )

    fun getAllFoodsController() =
        AllFoodsController(getScreensNavigator(), getGetAllFoodsUseCase(), getDeleteFoodUseCase())

    fun getAllMealsController() = AllMealsController(getScreensNavigator(), getGetAllMealsUseCase())

    fun getSelectFoodForMealController(currentMeal: Meal) =
        SelectFoodForMealController(getScreensNavigator(), getGetAllFoodsUseCase(), currentMeal)

    fun getAddEditMealController(
        viewModel: AddEditMealViewModel,
        newMealFood: MealFood?,
        currentMeal: Meal?
    ) = AddEditMealController(
        viewModel,
        getInsertMealUseCase(),
        getScreensNavigator(),
        newMealFood,
        currentMeal
    )

    private fun getScreensNavigator() =
        ScreensNavigator(androidComponentsConfig.navController)
}