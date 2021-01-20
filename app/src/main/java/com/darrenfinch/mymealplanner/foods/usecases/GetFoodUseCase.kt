package com.darrenfinch.mymealplanner.foods.usecases

import androidx.lifecycle.LiveData
import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.foods.models.Food

class GetFoodUseCase(private val repository: MainRepository) {
    fun fetchFood(foodId: Int): LiveData<Food> {
        return repository.getFood(foodId)
    }
}