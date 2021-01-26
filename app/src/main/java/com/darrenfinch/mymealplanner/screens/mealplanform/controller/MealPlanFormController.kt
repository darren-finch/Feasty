package com.darrenfinch.mymealplanner.screens.mealplanform.controller

import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.BackPressListener
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.common.constants.DefaultModels
import com.darrenfinch.mymealplanner.common.misc.ControllerSavedState
import com.darrenfinch.mymealplanner.screens.mealplanform.view.MealPlanFormViewMvc
import com.darrenfinch.mymealplanner.mealplans.usecases.InsertMealPlanUseCase
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan
import com.darrenfinch.mymealplanner.screens.mealplanform.MealPlanFormVm
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

class MealPlanFormController(
    private var viewModel: MealPlanFormVm,
    private val insertMealPlanUseCase: InsertMealPlanUseCase,
    private val screensNavigator: ScreensNavigator,
    private val backPressDispatcher: BackPressDispatcher,
    private val backgroundContext: CoroutineContext
) : BaseController, MealPlanFormViewMvc.Listener, BackPressListener {

    data class SavedState(val viewModel: MealPlanFormVm) :
        ControllerSavedState

    private lateinit var viewMvc: MealPlanFormViewMvc

    fun bindView(viewMvc: MealPlanFormViewMvc) {
        this.viewMvc = viewMvc
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

    fun bindMealDetailsToView() {
        viewMvc.bindMealPlanDetails(viewModel.getState())
    }

    override fun onDoneButtonClicked() {
        runBlocking(backgroundContext) {
            insertMealPlanUseCase.insertMealPlan(viewModel.getState())
        }
        screensNavigator.goBack()
    }

    override fun onNavigateUp() {
        screensNavigator.goBack()
    }

    override fun onTitleChange(newTitle: String) {
        viewModel.setTitle(newTitle)
    }

    override fun onRequiredCaloriesChange(newRequiredCalories: Int) {
        viewModel.setRequiredCalories(newRequiredCalories)
    }

    override fun onRequiredCarbohydratesChange(newRequiredCarbohydrates: Int) {
        viewModel.setRequiredCarbohydrates(newRequiredCarbohydrates)
    }

    override fun onRequiredFatsChange(newRequiredFats: Int) {
        viewModel.setRequiredFats(newRequiredFats)
    }

    override fun onRequiredProteinsChange(newRequiredProteins: Int) {
        viewModel.setRequiredProteins(newRequiredProteins)
    }

    override fun restoreState(state: ControllerSavedState) {
        (state as SavedState).let {
            viewModel = it.viewModel
        }
    }

    override fun getState(): ControllerSavedState {
        return SavedState(viewModel)
    }

    override fun onBackPressed(): Boolean {
        screensNavigator.goBack()
        return true
    }
}