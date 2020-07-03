package com.darrenfinch.mymealplanner.domain.usecases

import androidx.lifecycle.LiveData
import com.darrenfinch.mymealplanner.model.FoodsRepository
import com.darrenfinch.mymealplanner.model.data.Food

class GetAllFoodsUseCase(private val repository: FoodsRepository) {
    fun fetchAllFoods() : LiveData<List<Food>> {
        return repository.getFoods()
    }
}