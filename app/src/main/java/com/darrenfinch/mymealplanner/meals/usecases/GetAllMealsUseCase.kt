package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.common.extensions.parallelMap
import com.darrenfinch.mymealplanner.meals.models.mappers.dbMealToMeal
import com.darrenfinch.mymealplanner.meals.models.mappers.mealToUiMeal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.data.MainRepository
import kotlin.coroutines.coroutineContext

class GetAllMealsUseCase(private val repository: MainRepository, private val getMealUseCase: GetMealUseCase) {
    suspend fun getAllMeals(): List<UiMeal> {
        return repository.getAllMeals().parallelMap(coroutineContext) { dbMeal ->
            getMealUseCase.getMealNullable(dbMeal.id)
        }.filterNotNull()
    }
}