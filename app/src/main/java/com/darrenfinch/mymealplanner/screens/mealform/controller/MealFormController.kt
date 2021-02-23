package com.darrenfinch.mymealplanner.screens.mealform.controller

import com.darrenfinch.mymealplanner.common.controllers.StatefulController
import com.darrenfinch.mymealplanner.screens.mealform.view.MealFormViewMvc

interface MealFormController : StatefulController {
    fun bindView(viewMvc: MealFormViewMvc)

    fun onStart()

    fun onStop()

    fun getMealDetails()

    fun setArgs(mealId: Int)
}