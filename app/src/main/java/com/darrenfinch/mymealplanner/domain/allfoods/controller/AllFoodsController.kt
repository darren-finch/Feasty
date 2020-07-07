package com.darrenfinch.mymealplanner.domain.allfoods.controller

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.Constants
import com.darrenfinch.mymealplanner.common.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.allfoods.view.AllFoodsViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.DeleteFoodUseCase
import com.darrenfinch.mymealplanner.domain.usecases.GetAllFoodsUseCase

class AllFoodsController(
    private val screensNavigator: ScreensNavigator,
    private val getAllFoodsUseCase: GetAllFoodsUseCase,
    private val deleteFoodUseCase: DeleteFoodUseCase
) : AllFoodsViewMvc.Listener {

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
        screensNavigator.navigateToAddEditFoodScreen(Constants.DEFAULT_FOOD_ID)
    }

    override fun onItemEdit(foodId: Int) {
        screensNavigator.navigateToAddEditFoodScreen(foodId)
    }

    override fun onItemDelete(foodId: Int) {
        deleteFoodUseCase.deleteFood(foodId)
    }
}