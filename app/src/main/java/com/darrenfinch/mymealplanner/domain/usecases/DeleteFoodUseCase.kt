package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.model.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class DeleteFoodUseCase(private val repository: MainRepository) {
    fun deleteFood(id: Int) {
        repository.deleteFood(id)
    }
}