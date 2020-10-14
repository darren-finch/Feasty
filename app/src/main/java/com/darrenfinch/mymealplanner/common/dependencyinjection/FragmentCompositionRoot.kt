package com.darrenfinch.mymealplanner.common.dependencyinjection

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import com.darrenfinch.mymealplanner.common.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.foodform.controller.FoodFormController
import com.darrenfinch.mymealplanner.domain.foodform.controller.FoodFormViewModel
import com.darrenfinch.mymealplanner.domain.mealform.controller.MealFormController
import com.darrenfinch.mymealplanner.domain.mealform.controller.MealFormViewModel
import com.darrenfinch.mymealplanner.domain.allfoods.controller.AllFoodsController
import com.darrenfinch.mymealplanner.domain.allmeals.controller.AllMealsController
import com.darrenfinch.mymealplanner.domain.selectfoodformeal.controller.SelectFoodForMealController
import com.darrenfinch.mymealplanner.domain.selectmealfoodquantity.controller.SelectMealFoodQuantityController
import com.darrenfinch.mymealplanner.domain.usecases.*
import com.darrenfinch.mymealplanner.model.data.entities.Meal
import com.darrenfinch.mymealplanner.model.data.entities.MealFood

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

    private fun getMainRepository() = activityCompositionRoot.getMainRepository()

    private fun getGetAllFoodsUseCase() = GetAllFoodsUseCase(getMainRepository())
    private fun getGetFoodUseCase() = GetFoodUseCase(getMainRepository())
    private fun getInsertFoodUseCase() = InsertFoodUseCase(getMainRepository())
    private fun getUpdateFoodUseCase() = UpdateFoodUseCase(getMainRepository())
    private fun getDeleteFoodUseCase() = DeleteFoodUseCase(getMainRepository())
    private fun getInsertMealUseCase() = InsertMealUseCase(getMainRepository())
    private fun getGetAllMealsUseCase() = GetAllMealsUseCase(getMainRepository())

    //TODO: TEMP
    fun getDeleteAllMealsUseCase() = DeleteAllMealsUseCase(getMainRepository())

    fun getAddEditFoodController(viewModel: FoodFormViewModel) = FoodFormController(
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
        viewModel: MealFormViewModel,
        newMealFood: MealFood?,
        currentMeal: Meal?
    ) = MealFormController(
        viewModel,
        getInsertMealUseCase(),
        getScreensNavigator(),
        newMealFood,
        currentMeal
    )

    private fun getScreensNavigator() =
        ScreensNavigator(androidComponentsConfig.navController)
}