package com.darrenfinch.mymealplanner.screens.selectfoodformeal.controller

import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.screens.selectfoodformeal.controller.SelectFoodForMealFragment.Companion.FOOD_ID_RESULT
import com.darrenfinch.mymealplanner.screens.selectfoodformeal.view.SelectFoodForMealViewMvc
import com.darrenfinch.mymealplanner.common.misc.ControllerSavedState
import com.darrenfinch.mymealplanner.common.navigation.ScreenResult
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.foods.usecases.GetAllFoodsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class SelectFoodForMealController(
    private val getAllFoodsUseCase: GetAllFoodsUseCase,
    private val screensNavigator: ScreensNavigator,
    private val backgroundContext: CoroutineContext,
    private val uiContext: CoroutineContext
) : BaseController, SelectFoodForMealViewMvc.Listener {

    class SavedState : ControllerSavedState

    private lateinit var viewMvc: SelectFoodForMealViewMvc

    private var getAllFoodsJob: Job? = null

    fun bindView(viewMvc: SelectFoodForMealViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
        getAllFoodsJob?.cancel()
    }

    fun getAllFoodsAndBindToView() {
        getAllFoodsJob = CoroutineScope(backgroundContext).launch {
            val allFoods = getAllFoodsUseCase.getAllFoods()
            withContext(uiContext) {
                viewMvc.bindFoods(allFoods)
            }
        }
    }

    override fun restoreState(state: ControllerSavedState) {}
    override fun getState(): ControllerSavedState {
        return SavedState()
    }

    override fun onNavigateUp() {
        screensNavigator.navigateUp()
    }

    override fun onFoodChosen(foodId: Int) {
        screensNavigator.navigateUpWithResult(ScreenResult().apply {
            putSerializable(FOOD_ID_RESULT, foodId)
        })
    }
}