package com.darrenfinch.mymealplanner.foods.usecases

import androidx.lifecycle.LiveData
import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.foods.models.domain.Food

class GetAllFoodsUseCase(private val repository: MainRepository) {
    fun fetchAllFoods(): LiveData<List<Food>> {
        return repository.getFoods()
    }
}