package com.darrenfinch.mymealplanner.domain.selectfoodformeal.controller

import android.app.Application
import androidx.lifecycle.*
import com.darrenfinch.mymealplanner.model.data.Food

class SelectFoodForMealViewModel(application: Application) : AndroidViewModel(application) {

    private val allFoodsLiveData: MutableLiveData<List<Food>> = MutableLiveData()

    fun fetchAllFoods(): LiveData<List<Food>> {
        return allFoodsLiveData
    }
}