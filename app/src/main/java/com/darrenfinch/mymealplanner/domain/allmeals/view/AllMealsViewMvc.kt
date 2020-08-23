package com.darrenfinch.mymealplanner.domain.allmeals.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.model.data.entities.Meal

interface AllMealsViewMvc : ObservableViewMvc<AllMealsViewMvc.Listener> {
    interface Listener {
        fun addNewMealClicked()
        fun onMealEdit(mealId: Int)
        fun onMealDelete(meal: Meal)
    }

    fun bindMeals(newMeals: List<Meal>)
}