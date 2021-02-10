package com.darrenfinch.mymealplanner.common.dependencyinjection

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.EditMealFoodVm
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.controller.EditMealFoodController
import com.darrenfinch.mymealplanner.common.helpers.KeyboardHelper
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.foods.usecases.*
import com.darrenfinch.mymealplanner.mealplans.usecases.*
import com.darrenfinch.mymealplanner.meals.usecases.*
import com.darrenfinch.mymealplanner.screens.allfoods.controller.AllFoodsController
import com.darrenfinch.mymealplanner.screens.allmeals.controller.AllMealsController
import com.darrenfinch.mymealplanner.screens.foodform.FoodFormVm
import com.darrenfinch.mymealplanner.screens.foodform.controller.FoodFormControllerImpl
import com.darrenfinch.mymealplanner.screens.mealform.MealFormVm
import com.darrenfinch.mymealplanner.screens.mealform.controller.MealFormControllerImpl
import com.darrenfinch.mymealplanner.screens.mealplan.MealPlanVm
import com.darrenfinch.mymealplanner.screens.mealplan.controller.MealPlanControllerImpl
import com.darrenfinch.mymealplanner.screens.mealplanform.MealPlanFormVm
import com.darrenfinch.mymealplanner.screens.mealplanform.controller.MealPlanFormController
import com.darrenfinch.mymealplanner.screens.selectfoodformeal.controller.SelectFoodForMealController
import com.darrenfinch.mymealplanner.screens.selectmealplanmeal.controller.SelectMealPlanMealController
import kotlinx.coroutines.Dispatchers

//This composition root is scoped to a fragment, which is a controller
class ControllerCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {

    // Coroutine contexts
    val backgroundContext = Dispatchers.IO
    val uiContext = Dispatchers.Main

    // Object dependencies
    fun getDialogsEventBus() = activityCompositionRoot.getDialogsEventBus()
    fun getDialogsManager() = activityCompositionRoot.getDialogsManager()
    fun getViewMvcFactory(): ViewMvcFactory = ViewMvcFactory(getLayoutInflater())
    fun getBackPressDispatcher() = getActivity() as BackPressDispatcher
    private fun getActivity() = activityCompositionRoot.getActivity()
    private fun getContext() = getActivity()
    private fun getLayoutInflater(): LayoutInflater = LayoutInflater.from(getContext())
    fun getApplication(): Application = activityCompositionRoot.getApplication()
    private fun getMainRepository() = activityCompositionRoot.getMainRepository()
    private fun getScreensNavigator() = activityCompositionRoot.getScreensNavigator()
    private fun getToastsHelper() = activityCompositionRoot.getToastsHelper()
    private fun getSharedPreferencesHelper() = activityCompositionRoot.getSharedPreferencesHelper()
    private fun getKeyboardHelper(view: View) = KeyboardHelper(getContext(), view)


    // Use cases
    private fun getGetAllFoodsUseCase() = GetAllFoodsUseCase(getMainRepository())
    private fun getGetFoodUseCase() = GetFoodUseCase(getMainRepository())
    private fun getUpsertFoodUseCase() = UpsertFoodUseCase(getMainRepository())
    private fun getDeleteFoodUseCase() = DeleteFoodUseCase(getMainRepository())

    private fun getGetAllMealsUseCase() = GetAllMealsUseCase(getMainRepository())
    private fun getGetMealUseCase() = GetMealUseCase(getMainRepository())
    private fun getUpsertMealUseCase() = UpsertMealUseCase(getInsertMealUseCase(), getUpdateMealUseCase())
    private fun getInsertMealUseCase() = InsertMealUseCase(getMainRepository(), getInsertMealFoodUseCase())
    private fun getUpdateMealUseCase() = UpdateMealUseCase(getMainRepository(), getInsertMealFoodUseCase(), getUpdateMealFoodUseCase())
    private fun getDeleteMealUseCase() = DeleteMealUseCase(getMainRepository())

    private fun getGetAllMealPlansUseCase() = GetAllMealPlansUseCase(getMainRepository())
    private fun getGetMealPlanUseCase() = GetMealPlanUseCase(getMainRepository())
    private fun getInsertMealPlanUseCase() = InsertMealPlanUseCase(getMainRepository())
    private fun getUpdateMealPlanUseCase() = UpdateMealPlanUseCase(getMainRepository())
    private fun getDeleteMealPlanUseCase() = DeleteMealPlanUseCase(getMainRepository())

    private fun getGetMealsForMealPlanUseCase() = GetMealsForMealPlanUseCase(getMainRepository())
    private fun getInsertMealPlanMealUseCase() = InsertMealPlanMealUseCase(getMainRepository())
    private fun getUpdateMealPlanMealUseCase() = UpdateMealPlanMealUseCase(getMainRepository())
    private fun getDeleteMealPlanMealUseCase() = DeleteMealPlanMealUseCase(getMainRepository())

    private fun getGetMealFoodUseCase() = GetMealFoodUseCase(getMainRepository())
    private fun getInsertMealFoodUseCase() = InsertMealFoodUseCase(getMainRepository())
    private fun getUpdateMealFoodUseCase() = UpdateMealFoodUseCase(getMainRepository())

    // Dialog controllers

    fun getSelectFoodForMealController(
    ) = SelectFoodForMealController(
        getGetAllFoodsUseCase(),
        getScreensNavigator(),
        backgroundContext,
        uiContext
    )

    fun getSelectMealPlanMealController(
    ) = SelectMealPlanMealController(
        getGetAllMealsUseCase(),
        getScreensNavigator(),
        backgroundContext,
        uiContext
    )

    fun getEditMealController() = EditMealFoodController(
        getEditMealFoodVm(),
        dialogsManager = getDialogsManager(),
        dialogsEventBus = getDialogsEventBus()
    )

    // Normal screen controllers
    fun getFoodFormController() = FoodFormControllerImpl(
        getFoodFormVm(),
        getScreensNavigator(),
        getGetFoodUseCase(),
        getUpsertFoodUseCase(),
        getBackPressDispatcher(),
        backgroundContext,
        uiContext
    )

    fun getAllFoodsController() =
        AllFoodsController(
            getScreensNavigator(),
            getGetAllFoodsUseCase(),
            getDeleteFoodUseCase(),
            backgroundContext,
            uiContext
        )

    fun getAllMealsController() =
        AllMealsController(
            getScreensNavigator(),
            getGetAllMealsUseCase(),
            getDeleteMealUseCase(),
            backgroundContext,
            uiContext
        )

    fun getMealFormController() = MealFormControllerImpl(
        getMealFormVm(),
        getUpsertMealUseCase(),
        getGetMealUseCase(),
        getScreensNavigator(),
        getDialogsManager(),
        getDialogsEventBus(),
        getBackPressDispatcher(),
        backgroundContext,
        uiContext,
    )

    fun getMealPlanController() = MealPlanControllerImpl(
        getMealPlanVm(),
        getGetAllMealPlansUseCase(),
        getGetMealsForMealPlanUseCase(),
        getInsertMealPlanMealUseCase(),
        getDeleteMealPlanUseCase(),
        getDeleteMealPlanMealUseCase(),
        getScreensNavigator(),
        getToastsHelper(),
        getSharedPreferencesHelper(),
        backgroundContext,
        uiContext
    )

    fun getMealPlanFormController() = MealPlanFormController(
        getMealPlanFormVm(),
        getInsertMealPlanUseCase(),
        getScreensNavigator(),
        getBackPressDispatcher(),
        backgroundContext
    )


    // View models
    private fun getEditMealFoodVm() = EditMealFoodVm()
    fun getFoodFormVm() = FoodFormVm()
    fun getMealFormVm() = MealFormVm()
    fun getMealPlanFormVm() = MealPlanFormVm()
    private fun getMealPlanVm() = MealPlanVm()
}