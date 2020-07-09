package com.darrenfinch.mymealplanner.domain.usecases

import androidx.lifecycle.LiveData
import com.darrenfinch.mymealplanner.model.FoodsRepository
import com.darrenfinch.mymealplanner.model.data.Food

class GetSingleFoodUseCase(private val repository: FoodsRepository) {
    fun fetchFood(foodId: Int) : LiveData<Food> {
        return repository.fetchFood(foodId)
    }
}