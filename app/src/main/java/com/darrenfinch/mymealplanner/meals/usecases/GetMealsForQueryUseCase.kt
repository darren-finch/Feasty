package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.common.extensions.parallelMap
import com.darrenfinch.mymealplanner.data.MainRepository
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import kotlin.coroutines.coroutineContext

class GetMealsForQueryUseCase(private val repository: MainRepository, private val getMealUseCase: GetMealUseCase) {
    suspend fun getMealsForQuery(query: String): List<UiMeal> {
        return if (query.isEmpty()) {
            repository.getAllMeals().parallelMap(coroutineContext) { dbMeal ->
                getMealUseCase.getMealNullable(dbMeal.id)
            }.filterNotNull()
        } else {
            repository.getMealsForQuery(query).parallelMap(coroutineContext) { dbMeal ->
                getMealUseCase.getMealNullable(dbMeal.id)
            }.filterNotNull()
        }
    }
}