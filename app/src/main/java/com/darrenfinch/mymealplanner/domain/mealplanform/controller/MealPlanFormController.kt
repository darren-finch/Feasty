package com.darrenfinch.mymealplanner.domain.mealplanform.controller

import android.os.Bundle
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.BackPressListener
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.common.utils.DefaultModels
import com.darrenfinch.mymealplanner.domain.mealplanform.controller.MealPlanFormFragment.Companion.CONTROLLER_SAVED_STATE
import com.darrenfinch.mymealplanner.domain.mealplanform.view.MealPlanFormViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.InsertMealPlanUseCase
import com.darrenfinch.mymealplanner.model.data.entities.MealPlan

class MealPlanFormController(
    private val insertMealPlanUseCase: InsertMealPlanUseCase,
    private val screensNavigator: ScreensNavigator,
    private val backPressDispatcher: BackPressDispatcher
) : BaseController, MealPlanFormViewMvc.Listener, BackPressListener {

    data class SavedState(val mealPlanDetails: MealPlan) : BaseController.BaseSavedState

    private var mealPlanDetailsState = DefaultModels.defaultMealPlan

    private lateinit var viewMvc: MealPlanFormViewMvc

    fun bindView(viewMvc: MealPlanFormViewMvc) {
        this.viewMvc = viewMvc
        viewMvc.bindMealPlanDetails(mealPlanDetailsState)
    }

    fun onStart() {
        viewMvc.registerListener(this)
        backPressDispatcher.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
        backPressDispatcher.unregisterListener(this)
    }

    override fun onDoneButtonClicked(editedMealPlanDetails: MealPlan) {
        insertMealPlanUseCase.insertMealPlan(editedMealPlanDetails)
        screensNavigator.goBack()
    }

    override fun restoreState(state: BaseController.BaseSavedState) {
        (state as SavedState).let {
            mealPlanDetailsState = state.mealPlanDetails
        }
    }
    override fun getState(): BaseController.BaseSavedState {
        return SavedState(mealPlanDetailsState)
    }

    override fun onBackPressed(): Boolean {
        screensNavigator.goBack()
        return true
    }
}