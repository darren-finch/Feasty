package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.common.extensions.parallelMap
import com.darrenfinch.mymealplanner.mealplans.models.mappers.dbMealPlanMealToMealPlanMeal
import com.darrenfinch.mymealplanner.mealplans.models.mappers.mealPlanMealToUiMealPlanMeal
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlanMeal
import com.darrenfinch.mymealplanner.data.MainRepository
import kotlin.coroutines.coroutineContext

class GetMealsForMealPlanUseCase(private val repository: MainRepository) {
    suspend fun getMealsForMealPlan(mealPlanId: Int): List<UiMealPlanMeal> {
        // TODO: Consider speeding up this function
        // I think this is a good candidate for parallel mapping
        return repository.getMealsForMealPlan(mealPlanId).parallelMap(coroutineContext) { dbMealPlanMeal ->
            val dbMeal = repository.getMeal(dbMealPlanMeal.mealId)
            val dbMealPlan = repository.getMealPlan(dbMealPlanMeal.mealPlanId)
            val dbMealFoods = dbMeal?.id?.let { repository.getMealFoodsForMeal(it) }
            val dbFoodReferences = dbMealFoods?.map { dbMealFood -> repository.getFood(dbMealFood.foodId) }

            if(dbMeal != null && dbMealPlan != null && dbMealFoods != null && dbFoodReferences != null) {
                mealPlanMealToUiMealPlanMeal(dbMealPlanMealToMealPlanMeal(dbMealPlanMeal, dbMeal, dbMealPlan, dbMealFoods, dbFoodReferences))
            }
            else {
                null
            }

        }.filterNotNull()
    }
}