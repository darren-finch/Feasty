package com.darrenfinch.mymealplanner.domain.usecases

import androidx.lifecycle.LiveData
import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.model.data.entities.Food

class GetAllFoodsUseCase(private val repository: MainRepository) {
    fun fetchAllFoods(): LiveData<List<Food>> {
        return repository.getFoods()
    }
}