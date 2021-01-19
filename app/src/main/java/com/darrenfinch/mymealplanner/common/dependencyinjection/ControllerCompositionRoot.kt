package com.darrenfinch.mymealplanner.common.dependencyinjection

import android.app.Application
import android.view.LayoutInflater
import com.darrenfinch.mymealplanner.common.controllers.BaseDialog
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.domain.allfoods.controller.AllFoodsController
import com.darrenfinch.mymealplanner.domain.allmeals.controller.AllMealsController
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.controller.SelectFoodForMealController
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectFoodQuantityController
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.controller.SelectMealPlanMealController
import com.darrenfinch.mymealplanner.domain.foodform.controller.FoodFormController
import com.darrenfinch.mymealplanner.domain.mealform.controller.MealFormController
import com.darrenfinch.mymealplanner.domain.mealplan.controller.MealPlanController
import com.darrenfinch.mymealplanner.domain.mealplanform.controller.MealPlanFormController
import com.darrenfinch.mymealplanner.domain.usecases.*

//This composition root is scoped to a fragment, which is a controller
class ControllerCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {

    // Object dependencies
    private fun getActivity() = activityCompositionRoot.getActivity()
    private fun getContext() = getActivity()
    private fun getLayoutInflater(): LayoutInflater = LayoutInflater.from(getContext())
    fun getApplication(): Application = activityCompositionRoot.getApplication()
    fun getViewMvcFactory(): ViewMvcFactory = ViewMvcFactory(getLayoutInflater())
    private fun getMainRepository() = activityCompositionRoot.getMainRepository()
    private fun getScreensNavigator() = activityCompositionRoot.getScreensNavigator()
    private fun getDialogsManager() = activityCompositionRoot.getsDialogManager()
    fun getBackPressDispatcher() = getActivity() as BackPressDispatcher


    // Use cases
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


    // Dialog controllers
    fun getSelectMealFoodQuantityController(
    ) = SelectFoodQuantityController(
        getGetFoodUseCase(),
        getDialogsManager()
    )

    fun getSelectFoodForMealController(
    ) = SelectFoodForMealController(
        getGetAllFoodsUseCase(),
        getDialogsManager()
    )

    fun getSelectMealPlanMealController(
    ) = SelectMealPlanMealController(
        getGetAllMealsUseCase(),
        getDialogsManager()
    )

    // Normal screen controllers
    fun getFoodFormController() = FoodFormController(
        getScreensNavigator(),
        getGetFoodUseCase(),
        getInsertFoodUseCase(),
        getUpdateFoodUseCase(),
        getBackPressDispatcher()
    )

    fun getAllFoodsController() =
        AllFoodsController(getScreensNavigator(), getGetAllFoodsUseCase(), getDeleteFoodUseCase())

    fun getAllMealsController() =
        AllMealsController(getScreensNavigator(), getGetAllMealsUseCase(), getDeleteMealUseCase())

    fun getMealFormController() = MealFormController(
        getInsertMealUseCase(),
        getUpdateMealUseCase(),
        getGetMealUseCase(),
        getScreensNavigator(),
        getDialogsManager(),
        getBackPressDispatcher()
    )

    fun getMealPlanController() = MealPlanController(
        getGetAllMealPlansUseCase(),
        getGetMealsForMealPlanUseCase(),
        getInsertMealPlanMealUseCase(),
        getDeleteMealPlanUseCase(),
        getDeleteMealPlanMealUseCase(),
        getScreensNavigator(),
        getDialogsManager()
    )

    fun getMealPlanFormController() = MealPlanFormController(
        getInsertMealPlanUseCase(),
        getScreensNavigator(),
        getBackPressDispatcher()
    )
}