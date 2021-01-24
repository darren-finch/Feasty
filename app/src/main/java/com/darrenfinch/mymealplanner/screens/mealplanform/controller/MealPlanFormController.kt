package com.darrenfinch.mymealplanner.screens.mealplanform.controller

import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.BackPressListener
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.common.constants.DefaultModels
import com.darrenfinch.mymealplanner.screens.mealplanform.view.MealPlanFormViewMvc
import com.darrenfinch.mymealplanner.mealplans.usecases.InsertMealPlanUseCase
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MealPlanFormController(
    private val insertMealPlanUseCase: InsertMealPlanUseCase,
    private val screensNavigator: ScreensNavigator,
    private val backPressDispatcher: BackPressDispatcher,
    private val backgroundContext: CoroutineContext
) : BaseController, MealPlanFormViewMvc.Listener, BackPressListener {

    data class SavedState(val mealPlanDetails: UiMealPlan) : BaseController.BaseSavedState

    private var mealPlanDetailsState = DefaultModels.defaultUiMealPlan

    private lateinit var viewMvc: MealPlanFormViewMvc

    fun bindView(viewMvc: MealPlanFormViewMvc) {
        this.viewMvc = viewMvc
        viewMvc.bindMealPlanDetails(mealPlanDetailsState)
    }

    fun onStart() {
        viewMvc.registerListener(this)
        backPressDispatcher.registerListener(this)

        viewMvc.hideProgressIndication()
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
        backPressDispatcher.unregisterListener(this)
    }

    override fun onDoneButtonClicked(editedMealPlanDetails: UiMealPlan) {
        runBlocking(backgroundContext) {
            insertMealPlanUseCase.insertMealPlan(editedMealPlanDetails)
        }
        screensNavigator.goBack()
    }

    override fun restoreState(state: BaseController.BaseSavedState) {
        (state as SavedState).let {
            mealPlanDetailsState = state.mealPlanDetails
        }
    }

    override fun getState(): BaseController.BaseSavedState {
        return SavedState(viewMvc.getMealPlanDetails())
    }

    override fun onBackPressed(): Boolean {
        screensNavigator.goBack()
        return true
    }
}