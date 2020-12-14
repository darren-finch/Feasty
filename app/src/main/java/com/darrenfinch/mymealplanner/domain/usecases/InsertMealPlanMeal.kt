package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.model.data.entities.MealPlanMeal

class InsertMealPlanMeal(private val repository: MainRepository) {
    fun insertMealPlanMeal(mealPlanMeal: MealPlanMeal) {
        repository.insertMealPlanMeal(mealPlanMeal)
    }
}