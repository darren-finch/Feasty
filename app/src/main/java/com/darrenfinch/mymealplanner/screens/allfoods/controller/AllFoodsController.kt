package com.darrenfinch.mymealplanner.screens.allfoods.controller

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.misc.Constants
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.screens.allfoods.view.AllFoodsViewMvc
import com.darrenfinch.mymealplanner.foods.usecases.DeleteFoodUseCase
import com.darrenfinch.mymealplanner.foods.usecases.GetAllFoodsUseCase

class AllFoodsController(
    private val screensNavigator: ScreensNavigator,
    private val getAllFoodsUseCase: GetAllFoodsUseCase,
    private val deleteFoodUseCase: DeleteFoodUseCase
) : BaseController, AllFoodsViewMvc.Listener {

    class SavedState : BaseController.BaseSavedState

    private lateinit var viewMvc: AllFoodsViewMvc

    fun bindView(viewMvc: AllFoodsViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    fun fetchAllFoods(viewLifecycleOwner: LifecycleOwner) {
        getAllFoodsUseCase.fetchAllFoods().observe(viewLifecycleOwner, Observer { newFoods ->
            viewMvc.bindFoods(newFoods)
        })
    }

    override fun addNewFoodClicked() {
        screensNavigator.navigateToFoodFormScreen(Constants.INVALID_ID)
    }

    override fun onItemEdit(foodId: Int) {
        screensNavigator.navigateToFoodFormScreen(foodId)
    }

    override fun onItemDelete(foodId: Int) {
        deleteFoodUseCase.deleteFood(foodId)
    }

    override fun restoreState(state: BaseController.BaseSavedState) { }
    override fun getState(): BaseController.BaseSavedState {
        return SavedState()
    }
}