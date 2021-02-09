package com.darrenfinch.mymealplanner.screens.selectmealplanmeal.controller

import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.screens.selectmealplanmeal.controller.SelectMealPlanMealFragment.Companion.SELECTED_MEAL_RESULT
import com.darrenfinch.mymealplanner.screens.selectmealplanmeal.view.SelectMealPlanMealViewMvc
import com.darrenfinch.mymealplanner.common.controllers.ControllerSavedState
import com.darrenfinch.mymealplanner.common.logs.getClassTag
import com.darrenfinch.mymealplanner.common.navigation.ScreenResult
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.meals.usecases.GetAllMealsUseCase
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class SelectMealPlanMealController(
    private val getAllMealsUseCase: GetAllMealsUseCase,
    private val screensNavigator: ScreensNavigator,
    private val backgroundContext: CoroutineContext,
    private val uiContext: CoroutineContext
) : BaseController, SelectMealPlanMealViewMvc.Listener {

    class SavedState : ControllerSavedState

    private lateinit var viewMvc: SelectMealPlanMealViewMvc

    private var getAllMealsJob: Job? = null

    fun bindView(viewMvc: SelectMealPlanMealViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
        getAllMealsJob?.cancel()
    }

    fun getAllMealsAndBindToView() {
        getAllMealsJob = CoroutineScope(backgroundContext).launch {
            val allMeals = getAllMealsUseCase.getAllMeals()
            withContext(uiContext) {
                viewMvc.bindMeals(allMeals)
            }
        }
    }

    override fun onNavigateUp() {
        screensNavigator.navigateUp()
    }

    override fun onMealSelected(selectedMeal: UiMeal) {
        screensNavigator.navigateUpWithResult(ScreenResult(SelectMealPlanMealFragment.getClassTag()).apply {
            putSerializable(SELECTED_MEAL_RESULT, selectedMeal)
        })
    }

    override fun restoreState(state: ControllerSavedState) {}
    override fun getState(): ControllerSavedState {
        return SavedState()
    }
}