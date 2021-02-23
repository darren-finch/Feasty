package com.darrenfinch.mymealplanner.screens.mealplan.controller

import com.darrenfinch.mymealplanner.common.controllers.StatefulController
import com.darrenfinch.mymealplanner.screens.mealplan.view.MealPlanViewMvc

interface MealPlanController : StatefulController {
    fun bindView(viewMvc: MealPlanViewMvc)

    fun onStart()

    fun onStop()

    fun refresh()
}