package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.common.extensions.parallelMap
import com.darrenfinch.mymealplanner.mealplans.models.mappers.dbMealPlanMealToMealPlanMeal
import com.darrenfinch.mymealplanner.mealplans.models.mappers.mealPlanMealToUiMealPlanMeal
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlanMeal
import com.darrenfinch.mymealplanner.model.MainRepository
import kotlin.coroutines.coroutineContext

class GetMealsForMealPlanUseCase(private val repository: MainRepository) {
    suspend fun getMealsForMealPlan(mealPlanId: Int): List<UiMealPlanMeal> {
        // TODO: Consider speeding up this function
        // I think this is a good candidate for parallel mapping
        return repository.getMealsForMealPlan(mealPlanId).parallelMap(coroutineContext) {
            val dbMeal = repository.getMeal(it.mealId)
            val dbMealPlan = repository.getMealPlan(it.mealPlanId)
            val dbMealFoods = repository.getMealFoodsForMeal(dbMeal.id)
            val dbFoodReferences = dbMealFoods.map { dbMealFood -> repository.getFood(dbMealFood.foodId) }
            mealPlanMealToUiMealPlanMeal(dbMealPlanMealToMealPlanMeal(it, dbMeal, dbMealPlan, dbMealFoods, dbFoodReferences))
        }
    }
}