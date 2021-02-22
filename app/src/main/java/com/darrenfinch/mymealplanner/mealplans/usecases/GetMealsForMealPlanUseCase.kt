package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.common.extensions.parallelMap
import com.darrenfinch.mymealplanner.data.MainRepository
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlanMeal
import com.darrenfinch.mymealplanner.meals.usecases.GetMealUseCase
import kotlin.coroutines.coroutineContext

class GetMealsForMealPlanUseCase(private val repository: MainRepository, private val getMealUseCase: GetMealUseCase) {
    suspend fun getMealsForMealPlan(mealPlanId: Int): List<UiMealPlanMeal> {
        return repository.getMealsForMealPlan(mealPlanId).parallelMap(coroutineContext) { dbMealPlanMeal ->
            val mealReference = getMealUseCase.getMealNullable(dbMealPlanMeal.referenceMealId)

            // This is a tad tacky, but it certainly makes the logic in this function much easier
            if(mealReference != null) {
                UiMealPlanMeal(
                    id = dbMealPlanMeal.id,
                    mealId = dbMealPlanMeal.referenceMealId,
                    mealPlanId = dbMealPlanMeal.mealPlanOwnerId,
                    title = mealReference.title,
                    foods = mealReference.foods
                )
            } else {
                null
            }
        }.filterNotNull()
    }
}