package com.darrenfinch.mymealplanner.screens.selectmealplanmeal.controller

import com.darrenfinch.mymealplanner.common.controllers.ControllerSavedState
import com.darrenfinch.mymealplanner.common.controllers.StatefulController
import com.darrenfinch.mymealplanner.common.navigation.ScreenDataReturnBuffer
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.meals.usecases.GetMealsForQueryUseCase
import com.darrenfinch.mymealplanner.screens.selectmealplanmeal.SelectMealPlanMealSavableData
import com.darrenfinch.mymealplanner.screens.selectmealplanmeal.view.SelectMealPlanMealViewMvc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class SelectMealPlanMealController(
    private var savableData: SelectMealPlanMealSavableData,
    private val getMealsForQuery: GetMealsForQueryUseCase,
    private val screensNavigator: ScreensNavigator,
    private val screenDataReturnBuffer: ScreenDataReturnBuffer,
    private val backgroundContext: CoroutineContext,
    private val uiContext: CoroutineContext
) : StatefulController, SelectMealPlanMealViewMvc.Listener {

    data class SavedState(val savableData: SelectMealPlanMealSavableData) : ControllerSavedState

    private sealed class ScreenState {
        object Loading : ScreenState()
        class HasData(val meals: List<UiMeal>) : ScreenState()
    }

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
        setScreenState(ScreenState.Loading)
        getAllMealsJob = CoroutineScope(backgroundContext).launch {
            val mealsFromQuery = getMealsForQuery.getMealsForQuery(savableData.getCurQuery())
            withContext(uiContext) {
                setScreenState(ScreenState.HasData(mealsFromQuery))
            }
        }
    }

    private fun setScreenState(state: ScreenState) {
        when (state) {
            is ScreenState.Loading -> {
                viewMvc.showProgressIndication()
            }
            is ScreenState.HasData -> {
                viewMvc.hideProgressIndication()
                viewMvc.bindMeals(state.meals)
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

    override fun onQuerySubmitted(query: String) {
        savableData.setCurQuery(query)
        getAllMeals()
    }

    override fun restoreState(state: ControllerSavedState) {
        (state as SavedState).let {
            savableData = it.savableData
        }
    }
    override fun getState(): ControllerSavedState {
        return SavedState(savableData)
    }
}