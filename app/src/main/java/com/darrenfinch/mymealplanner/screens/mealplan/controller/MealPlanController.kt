package com.darrenfinch.mymealplanner.screens.mealplan.controller

import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.screens.mealplan.view.MealPlanViewMvc

interface MealPlanController : BaseController {
    fun bindView(viewMvc: MealPlanViewMvc)

    fun onStart()

    fun onStop()

    fun getAllMealPlans()
}