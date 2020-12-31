package com.darrenfinch.mymealplanner.domain.mealplanform.controller

import com.darrenfinch.mymealplanner.common.misc.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.mealplanform.view.MealPlanFormViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.InsertMealPlanUseCase
import com.darrenfinch.mymealplanner.model.data.entities.MealPlan

class MealPlanFormController(private val viewModel: MealPlanFormViewModel, private val insertMealPlanUseCase: InsertMealPlanUseCase, private val screensNavigator: ScreensNavigator) : MealPlanFormViewMvc.Listener {
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
        screensNavigator.navigateFromSelectMealPlanMealScreenToMealPlanScreen()
    }
}