package com.darrenfinch.mymealplanner.screens.allfoods.controller

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.misc.ControllerSavedState
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.foods.usecases.DeleteFoodUseCase
import com.darrenfinch.mymealplanner.foods.usecases.GetAllFoodsUseCase
import com.darrenfinch.mymealplanner.screens.allfoods.view.AllFoodsViewMvc
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AllFoodsController(
    private val screensNavigator: ScreensNavigator,
    private val getAllFoodsUseCase: GetAllFoodsUseCase,
    private val deleteFoodUseCase: DeleteFoodUseCase,
    private val backgroundContext: CoroutineContext,
    private val uiContext: CoroutineContext
) : BaseController, AllFoodsViewMvc.Listener {

    class SavedState : ControllerSavedState

    private lateinit var viewMvc: AllFoodsViewMvc

    private var getAllFoodsJob: Job? = null

    fun bindView(viewMvc: AllFoodsViewMvc) {
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
        viewMvc.showProgressIndication()
        getAllFoodsJob = CoroutineScope(backgroundContext).launch {
            val allFoods = getAllFoodsUseCase.getAllFoods()
            withContext(uiContext) {
                viewMvc.hideProgressIndication()
                viewMvc.bindFoods(allFoods)
            }
        }
    }

    override fun onAddNewFoodClicked() {
        screensNavigator.toFoodFormScreen(Constants.INVALID_ID)
    }

    override fun onFoodEdit(foodId: Int) {
        screensNavigator.toFoodFormScreen(foodId)
    }

    override fun onFoodDelete(foodId: Int) {
        runBlocking(backgroundContext) {
            deleteFoodUseCase.deleteFood(foodId)
        }
        getAllFoodsAndBindToView()
    }

    override fun restoreState(state: ControllerSavedState) { }
    override fun getState(): ControllerSavedState {
        return SavedState()
    }
}