package com.darrenfinch.mymealplanner.screens.selectmealplanmeal.controller

import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.screens.selectmealplanmeal.view.SelectMealPlanMealViewMvc
import com.darrenfinch.mymealplanner.common.controllers.ControllerSavedState
import com.darrenfinch.mymealplanner.common.navigation.ScreenDataReturnBuffer
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
    private val screenDataReturnBuffer: ScreenDataReturnBuffer,
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

    fun getAllMeals() {
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

    override fun onMealChosen(chosenMeal: UiMeal) {
        screenDataReturnBuffer.putData(chosenMeal, SelectMealPlanMealFragment.ASYNC_COMPLETION_TOKEN)
        screensNavigator.navigateUp()
    }

    override fun restoreState(state: ControllerSavedState) {}
    override fun getState(): ControllerSavedState {
        return SavedState()
    }
}