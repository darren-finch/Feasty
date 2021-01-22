package com.darrenfinch.mymealplanner.screens.allfoods.controller

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.controllers.BaseController
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

    class SavedState : BaseController.BaseSavedState

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
        getAllFoodsJob = CoroutineScope(backgroundContext).launch {
            val allFoods = getAllFoodsUseCase.getAllFoods()
            withContext(uiContext) {
                viewMvc.bindFoods(allFoods)
            }
        }
    }

    override fun addNewFoodClicked() {
        screensNavigator.navigateToFoodFormScreen(Constants.INVALID_ID)
    }

    override fun onItemEdit(foodId: Int) {
        screensNavigator.navigateToFoodFormScreen(foodId)
    }

    override fun onItemDelete(foodId: Int) {
        runBlocking(backgroundContext) {
            deleteFoodUseCase.deleteFood(foodId)
        }
        getAllFoodsAndBindToView()
    }

    override fun restoreState(state: BaseController.BaseSavedState) { }
    override fun getState(): BaseController.BaseSavedState {
        return SavedState()
    }
}