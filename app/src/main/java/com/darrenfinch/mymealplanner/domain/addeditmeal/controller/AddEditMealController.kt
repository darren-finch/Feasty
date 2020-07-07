package com.darrenfinch.mymealplanner.domain.addeditmeal.controller

import com.darrenfinch.mymealplanner.common.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.addeditmeal.view.AddEditMealViewMvc

class AddEditMealController(
    private val screensNavigator: ScreensNavigator
) : AddEditMealViewMvc.Listener {
    private lateinit var viewMvc: AddEditMealViewMvc

    fun bindView(viewMvc: AddEditMealViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    override fun addNewFoodClicked() {
        screensNavigator.navigateToSelectFoodForMealScreen()
    }
}