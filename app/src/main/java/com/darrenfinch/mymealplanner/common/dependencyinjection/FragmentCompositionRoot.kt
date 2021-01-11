package com.darrenfinch.mymealplanner.common.dependencyinjection

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import com.darrenfinch.mymealplanner.common.misc.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.foodform.controller.FoodFormController
import com.darrenfinch.mymealplanner.domain.foodform.controller.FoodFormViewModel
import com.darrenfinch.mymealplanner.domain.mealform.controller.MealFormController
import com.darrenfinch.mymealplanner.domain.mealform.controller.MealFormViewModel
import com.darrenfinch.mymealplanner.domain.allfoods.controller.AllFoodsController
import com.darrenfinch.mymealplanner.domain.allmeals.controller.AllMealsController
import com.darrenfinch.mymealplanner.domain.mealplan.controller.MealPlanController
import com.darrenfinch.mymealplanner.domain.mealplan.controller.MealPlanViewModel
import com.darrenfinch.mymealplanner.domain.mealplanform.controller.MealPlanFormController
import com.darrenfinch.mymealplanner.domain.mealplanform.controller.MealPlanFormViewModel
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.controller.SelectFoodForMealController
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectMealFoodQuantityController
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.controller.SelectMealPlanMealController
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

    private fun getGetAllMealsUseCase() = GetAllMealsUseCase(getMainRepository())
    private fun getGetMealUseCase() = GetMealUseCase(getMainRepository())
    private fun getInsertMealUseCase() = InsertMealUseCase(getMainRepository())
    private fun getUpdateMealUseCase() = UpdateMealUseCase(getMainRepository())
    private fun getDeleteMealUseCase() = DeleteMealUseCase(getMainRepository())

    private fun getGetAllMealPlansUseCase() = GetAllMealPlansUseCase(getMainRepository())
    private fun getInsertMealPlanUseCase() = InsertMealPlanUseCase(getMainRepository())
    private fun getUpdateMealPlanUseCase() = UpdateMealPlanUseCase(getMainRepository())
    private fun getDeleteMealPlanUseCase() = DeleteMealPlanUseCase(getMainRepository())

    private fun getGetMealsForMealPlanUseCase() = GetMealsForMealPlanUseCase(getMainRepository())
    private fun getInsertMealPlanMealUseCase() = InsertMealPlanMealUseCase(getMainRepository())
    private fun getUpdateMealPlanMealUseCase() = UpdateMealPlanMealUseCase(getMainRepository())
    private fun getDeleteMealPlanMealUseCase() = DeleteMealPlanMealUseCase(getMainRepository())

//    //TODO: TEMP
//    fun getDeleteAllMealsUseCase() = DeleteAllMealsUseCase(getMainRepository())

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

    fun getAllMealsController() =
        AllMealsController(getScreensNavigator(), getGetAllMealsUseCase(), getDeleteMealUseCase())

    fun getMealFormController(
        viewModel: MealFormViewModel,
        newMealFood: MealFood?,
        currentMeal: Meal?,
        mealId: Int
    ) = MealFormController(
        viewModel,
        getInsertMealUseCase(),
        getUpdateMealUseCase(),
        getGetMealUseCase(),
        getScreensNavigator(),
        newMealFood,
        currentMeal,
        mealId
    )

    fun getMealPlanController(viewModel: MealPlanViewModel) =
        MealPlanController(
            getGetAllMealPlansUseCase(),
            getGetMealsForMealPlanUseCase(),
            getDeleteMealPlanUseCase(),
            getDeleteMealPlanMealUseCase(),
            viewModel,
            getScreensNavigator()
        )

    fun getMealPlanFormController(viewModel: MealPlanFormViewModel) = MealPlanFormController(viewModel, getInsertMealPlanUseCase(), getScreensNavigator())

    fun getSelectFoodForMealController(currentMeal: Meal) = SelectFoodForMealController(getGetAllFoodsUseCase(), getScreensNavigator(), currentMeal)

    private fun getScreensNavigator() =
        ScreensNavigator(androidComponentsConfig.navController)

    fun getSelectMealPlanMealController(mealPlanId: Int) = SelectMealPlanMealController(mealPlanId, getGetAllMealsUseCase(),
        getInsertMealPlanMealUseCase(), getScreensNavigator())

}