package com.darrenfinch.mymealplanner.common.dependencyinjection

import android.app.Application
import android.view.LayoutInflater
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodformeal.controller.SelectFoodForMealController
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.controller.SelectFoodQuantityController
import com.darrenfinch.mymealplanner.common.dialogs.selectmealplanmeal.controller.SelectMealPlanMealController
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.foods.usecases.*
import com.darrenfinch.mymealplanner.mealplans.usecases.*
import com.darrenfinch.mymealplanner.meals.usecases.*
import com.darrenfinch.mymealplanner.screens.allfoods.controller.AllFoodsController
import com.darrenfinch.mymealplanner.screens.allmeals.controller.AllMealsController
import com.darrenfinch.mymealplanner.screens.foodform.controller.FoodFormController
import com.darrenfinch.mymealplanner.screens.mealform.controller.MealFormController
import com.darrenfinch.mymealplanner.screens.mealplan.controller.MealPlanController
import com.darrenfinch.mymealplanner.screens.mealplanform.controller.MealPlanFormController
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
        getDialogsManager(),
        getDialogsEventBus(),
        backgroundContext,
        uiContext
    )

    fun getSelectFoodForMealController(
    ) = SelectFoodForMealController(
        getGetAllFoodsUseCase(),
        getDialogsManager(),
        getDialogsEventBus(),
        backgroundContext,
        uiContext
    )

    fun getSelectMealPlanMealController(
    ) = SelectMealPlanMealController(
        getGetAllMealsUseCase(),
        getDialogsManager(),
        getDialogsEventBus(),
        backgroundContext,
        uiContext
    )

    // Normal screen controllers
    fun getFoodFormController() = FoodFormController(
        getScreensNavigator(),
        getGetFoodUseCase(),
        getInsertFoodUseCase(),
        getUpdateFoodUseCase(),
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

    fun getMealFormController() = MealFormController(
        getInsertMealUseCase(),
        getUpdateMealUseCase(),
        getGetMealUseCase(),
        getScreensNavigator(),
        getDialogsManager(),
        getDialogsEventBus(),
        getBackPressDispatcher(),
        backgroundContext,
        uiContext
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
        getBackPressDispatcher(),
        backgroundContext
    )
}