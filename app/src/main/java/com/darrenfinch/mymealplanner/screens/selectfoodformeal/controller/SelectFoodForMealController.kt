package com.darrenfinch.mymealplanner.screens.selectfoodformeal.controller

import com.darrenfinch.mymealplanner.common.controllers.ControllerSavedState
import com.darrenfinch.mymealplanner.common.controllers.StatefulController
import com.darrenfinch.mymealplanner.common.navigation.ScreenDataReturnBuffer
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.foods.usecases.GetAllFoodsUseCase
import com.darrenfinch.mymealplanner.foods.usecases.GetFoodsFromQueryUseCase
import com.darrenfinch.mymealplanner.screens.selectfoodformeal.SelectFoodForMealSavableData
import com.darrenfinch.mymealplanner.screens.selectfoodformeal.view.SelectFoodForMealViewMvc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class SelectFoodForMealController(
    private var savableData: SelectFoodForMealSavableData,
    private val getFoodsFromQuery: GetFoodsFromQueryUseCase,
    private val screensNavigator: ScreensNavigator,
    private val screenDataReturnBuffer: ScreenDataReturnBuffer,
    private val backgroundContext: CoroutineContext,
    private val uiContext: CoroutineContext
) : StatefulController, SelectFoodForMealViewMvc.Listener {

    data class SavedState(val savableData: SelectFoodForMealSavableData) : ControllerSavedState

    private sealed class ScreenState {
        object Loading : ScreenState()
        class HasData(val foods: List<UiFood>) : ScreenState()
    }

    private lateinit var viewMvc: SelectFoodForMealViewMvc

    private var getFoodsJob: Job? = null

    fun bindView(viewMvc: SelectFoodForMealViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
        getFoods()
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
        getFoodsJob?.cancel()
    }

    fun getFoods() {
        setScreenState(ScreenState.Loading)
        getFoodsJob = CoroutineScope(backgroundContext).launch {
            val foodsFromQuery = getFoodsFromQuery.getFoodsFromQuery(savableData.getCurQuery())
            withContext(uiContext) {
                setScreenState(ScreenState.HasData(foodsFromQuery))
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
                viewMvc.bindFoods(state.foods)
            }
        }
    }

    override fun restoreState(state: ControllerSavedState) {
        (state as SavedState).let {
            savableData = state.savableData
        }
    }
    override fun getState(): ControllerSavedState {
        return SavedState(savableData)
    }

    override fun onNavigateUp() {
        screensNavigator.navigateUp()
    }

    override fun onFoodChosen(food: UiFood) {
        screenDataReturnBuffer.putData(food, SelectFoodForMealFragment.ASYNC_COMPLETION_TOKEN)
        screensNavigator.navigateUp()
    }

    override fun onQuerySubmitted(query: String) {
        savableData.setCurQuery(query)
        getFoods()
    }
}