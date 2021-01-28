package com.darrenfinch.mymealplanner.screens.mealform.controller

import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.screens.mealform.view.MealFormViewMvc

interface MealFormController : BaseController {
    fun bindView(viewMvc: MealFormViewMvc)

    fun onStart()

    fun onStop()

    fun getMealDetailsIfPossibleAndBindToView()

    fun setArgs(mealId: Int)
}