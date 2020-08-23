package com.darrenfinch.mymealplanner.domain.usecases

import androidx.lifecycle.LiveData
import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.model.data.entities.Food

class GetFoodUseCase(private val repository: MainRepository) {
    fun fetchFood(foodId: Int): LiveData<Food> {
        return repository.getFood(foodId)
    }
}