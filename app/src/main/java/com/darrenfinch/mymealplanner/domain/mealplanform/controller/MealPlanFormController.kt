package com.darrenfinch.mymealplanner.domain.mealplanform.controller

import android.os.Bundle
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.mealplanform.view.MealPlanFormViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.InsertMealPlanUseCase
import com.darrenfinch.mymealplanner.model.data.entities.MealPlan

class MealPlanFormController(
    private val insertMealPlanUseCase: InsertMealPlanUseCase,
    private val screensNavigator: ScreensNavigator
) : BaseController, MealPlanFormViewMvc.Listener {
    private lateinit var viewMvc: MealPlanFormViewMvc

    fun bindView(viewMvc: MealPlanFormViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    override fun onDoneClicked(finalMealPlan: MealPlan) {
        insertMealPlanUseCase.insertMealPlan(finalMealPlan)
        screensNavigator.goBack()
    }

    override fun setState(state: Bundle?) {}
    override fun getState(): Bundle {
        return Bundle()
    }
}