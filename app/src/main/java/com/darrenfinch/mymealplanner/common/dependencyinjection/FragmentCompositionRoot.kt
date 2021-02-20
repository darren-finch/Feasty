package com.darrenfinch.mymealplanner.common.dependencyinjection

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.EditMealFoodDialogData
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.controller.EditMealFoodController
import com.darrenfinch.mymealplanner.common.helpers.KeyboardHelper
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.foods.usecases.*
import com.darrenfinch.mymealplanner.mealplans.usecases.*
import com.darrenfinch.mymealplanner.meals.usecases.*
import com.darrenfinch.mymealplanner.screens.allfoods.controller.AllFoodsController
import com.darrenfinch.mymealplanner.screens.allmeals.controller.AllMealsController
import com.darrenfinch.mymealplanner.screens.foodform.FoodFormData
import com.darrenfinch.mymealplanner.screens.foodform.controller.FoodFormControllerImpl
import com.darrenfinch.mymealplanner.screens.mealform.MealFormData
import com.darrenfinch.mymealplanner.screens.mealform.controller.MealFormControllerImpl
import com.darrenfinch.mymealplanner.screens.mealplan.controller.GetValidIndexHelper
import com.darrenfinch.mymealplanner.screens.mealplan.controller.MealPlanSavableData
import com.darrenfinch.mymealplanner.screens.mealplan.controller.MealPlanScreenStatePresenter
import com.darrenfinch.mymealplanner.screens.mealplan.controller.RefreshMealPlanScreenUseCase
import com.darrenfinch.mymealplanner.screens.mealplan.controller.MealPlanControllerImpl
import com.darrenfinch.mymealplanner.screens.mealplanform.MealPlanFormData
import com.darrenfinch.mymealplanner.screens.mealplanform.controller.MealPlanFormController
import com.darrenfinch.mymealplanner.screens.selectfoodformeal.controller.SelectFoodForMealController
import com.darrenfinch.mymealplanner.screens.selectmealplanmeal.controller.SelectMealPlanMealController
import kotlinx.coroutines.Dispatchers

//This composition root is scoped to a fragment, which is a controller
class FragmentCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {

    // Coroutine contexts
    val backgroundContext = Dispatchers.IO
    val uiContext = Dispatchers.Main

    // Object dependencies
    fun getDialogsEventBus() = activityCompositionRoot.getDialogsEventBus()
    fun getScreenDataReturnBuffer() = activityCompositionRoot.getScreenDataReturnBuffer()
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
    private fun getGetValidIndexHelper() = GetValidIndexHelper()


    // Use cases
    private fun getGetAllFoodsUseCase() = GetAllFoodsUseCase(getMainRepository())
    private fun getGetFoodUseCase() = GetFoodUseCase(getMainRepository())
    private fun getUpsertFoodUseCase() = UpsertFoodUseCase(getMainRepository())
    private fun getDeleteFoodUseCase() = DeleteFoodUseCase(getMainRepository())

    private fun getGetAllMealsUseCase() =
        GetAllMealsUseCase(getMainRepository(), getGetMealUseCase())

    private fun getGetMealUseCase() = GetMealUseCase(getMainRepository())
    private fun getUpsertMealUseCase() =
        UpsertMealUseCase(getInsertMealUseCase(), getUpdateMealUseCase())

    private fun getInsertMealUseCase() =
        InsertMealUseCase(getMainRepository(), getInsertMealFoodUseCase())

    private fun getUpdateMealUseCase() = UpdateMealUseCase(
        getMainRepository(),
        getInsertMealFoodUseCase(),
        getUpdateMealFoodUseCase()
    )

    private fun getDeleteMealUseCase() = DeleteMealUseCase(getMainRepository())

    private fun getGetAllMealPlansUseCase() = GetAllMealPlansUseCase(getMainRepository())
    private fun getInsertMealPlanUseCase() = InsertMealPlanUseCase(getMainRepository())
    private fun getDeleteMealPlanUseCase() = DeleteMealPlanUseCase(getMainRepository())

    private fun getGetMealsForMealPlanUseCase() =
        GetMealsForMealPlanUseCase(getMainRepository(), getGetMealUseCase())

    private fun getInsertMealPlanMealUseCase() = InsertMealPlanMealUseCase(getMainRepository())
    private fun getDeleteMealPlanMealUseCase() = DeleteMealPlanMealUseCase(getMainRepository())

    private fun getInsertMealFoodUseCase() = InsertMealFoodUseCase(getMainRepository())
    private fun getUpdateMealFoodUseCase() = UpdateMealFoodUseCase(getMainRepository())

    private fun getRefreshMealPlanScreenUseCase() = RefreshMealPlanScreenUseCase(
        getGetAllMealPlansUseCase(),
        getGetMealsForMealPlanUseCase(),
        getSharedPreferencesHelper(),
        getGetValidIndexHelper()
    )

    // Dialog controllers

    fun getSelectFoodForMealController(
    ) = SelectFoodForMealController(
        getGetAllFoodsUseCase(),
        getScreensNavigator(),
        getScreenDataReturnBuffer(),
        backgroundContext,
        uiContext
    )

    fun getSelectMealPlanMealController(
    ) = SelectMealPlanMealController(
        getGetAllMealsUseCase(),
        getScreensNavigator(),
        getScreenDataReturnBuffer(),
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
        getScreenDataReturnBuffer(),
        getDialogsManager(),
        getDialogsEventBus(),
        getBackPressDispatcher(),
        backgroundContext,
        uiContext,
    )

    fun getMealPlanController() = MealPlanControllerImpl(
        getMealPlanVm(),
        getMealPlanScreenStatePresenter(),
        getRefreshMealPlanScreenUseCase(),
        getInsertMealPlanMealUseCase(),
        getDeleteMealPlanUseCase(),
        getDeleteMealPlanMealUseCase(),
        getScreensNavigator(),
        getScreenDataReturnBuffer(),
        getToastsHelper(),
        getSharedPreferencesHelper(),
        backgroundContext,
        uiContext
    )

    private fun getMealPlanScreenStatePresenter() = MealPlanScreenStatePresenter()

    fun getMealPlanFormController() = MealPlanFormController(
        getMealPlanFormVm(),
        getInsertMealPlanUseCase(),
        getScreensNavigator(),
        getBackPressDispatcher(),
        backgroundContext
    )


    // View models
    private fun getEditMealFoodVm() = EditMealFoodDialogData()
    fun getFoodFormVm() = FoodFormData()
    fun getMealFormVm() = MealFormData()
    fun getMealPlanFormVm() = MealPlanFormData()
    private fun getMealPlanVm() = MealPlanSavableData()
}