package com.darrenfinch.mymealplanner.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darrenfinch.mymealplanner.model.MealPlannerRepository
import com.darrenfinch.mymealplanner.model.room.Meal
import kotlinx.coroutines.launch

class MealPlanViewModel(private val repo: MealPlannerRepository) : ViewModel()
{
    val allMealsLiveData: MutableLiveData<List<Meal>> = MutableLiveData()

    fun getMeals() : LiveData<List<Meal>>
    {
        viewModelScope.launch {
            allMealsLiveData.postValue(repo.getMeals())
        }
        return allMealsLiveData
    }
}