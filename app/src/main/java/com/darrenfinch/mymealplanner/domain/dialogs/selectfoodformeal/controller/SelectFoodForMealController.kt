package com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.controller

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.view.SelectFoodForMealViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.GetAllFoodsUseCase
import com.darrenfinch.mymealplanner.model.data.entities.Meal

class SelectFoodForMealController(
    private val getAllFoodsUseCase: GetAllFoodsUseCase,
    private val screensNavigator: ScreensNavigator,
    private val currentMeal: Meal
) : SelectFoodForMealViewMvc.Listener {

    private lateinit var viewMvc: SelectFoodForMealViewMvc

    fun bindView(viewMvc: SelectFoodForMealViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    override fun onFoodChosen(id: Int) {
        screensNavigator.navigateFromSelectFoodForMealScreenToSelectFoodQuantityScreen(id, currentMeal)
    }

    fun fetchAllFoods(viewLifecycleOwner: LifecycleOwner) {
        getAllFoodsUseCase.fetchAllFoods().observe(viewLifecycleOwner, Observer {
            viewMvc.bindFoods(it)
        })
    }
}