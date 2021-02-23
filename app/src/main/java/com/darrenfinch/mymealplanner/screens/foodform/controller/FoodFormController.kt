package com.darrenfinch.mymealplanner.screens.foodform.controller

import com.darrenfinch.mymealplanner.common.controllers.StatefulController
import com.darrenfinch.mymealplanner.common.navigation.BackPressListener
import com.darrenfinch.mymealplanner.screens.foodform.view.FoodFormViewMvc

interface FoodFormController : StatefulController, FoodFormViewMvc.Listener, BackPressListener {
    fun bindView(viewMvc: FoodFormViewMvc)
    fun onStart()
    fun onStop()
    fun getFoodDetails()
    fun setArgs(foodId: Int)
}