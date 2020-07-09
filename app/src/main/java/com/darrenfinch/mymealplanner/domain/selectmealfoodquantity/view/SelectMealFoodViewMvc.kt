package com.darrenfinch.mymealplanner.domain.selectmealfoodquantity.view

import android.app.Dialog
import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.model.data.Food
import com.darrenfinch.mymealplanner.model.data.MealFood

interface SelectMealFoodViewMvc : ObservableViewMvc<SelectMealFoodViewMvc.Listener> {
    interface Listener {
        fun onMealFoodQuantityChosen(mealFood: MealFood)
    }

    fun bindFood(food: Food)
    fun makeDialog() : Dialog
}
