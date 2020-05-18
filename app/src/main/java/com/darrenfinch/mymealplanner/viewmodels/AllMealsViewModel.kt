package com.darrenfinch.mymealplanner.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.darrenfinch.mymealplanner.di.AppModule
import com.darrenfinch.mymealplanner.di.DaggerApplicationComponent
import com.darrenfinch.mymealplanner.di.RoomModule
import com.darrenfinch.mymealplanner.model.MealPlannerRepository
import com.darrenfinch.mymealplanner.model.room.DatabaseMeal
import com.darrenfinch.mymealplanner.model.room.Meal
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllMealsViewModel(application: Application) : AndroidViewModel(application)
{
    @Inject
    lateinit var repo: MealPlannerRepository
    init
    {
        DaggerApplicationComponent.builder()
            .appModule(AppModule(application))
            .roomModule(RoomModule(application))
            .build()
            .inject(this)
    }

    private val allMealsLiveData: MutableLiveData<List<Meal>> = MutableLiveData()

    fun getMeals() : LiveData<List<Meal>>
    {
        viewModelScope.launch {
            allMealsLiveData.postValue(repo.getMeals())
        }
        return allMealsLiveData
    }
    fun insertMeal(meal: DatabaseMeal) = repo.insertMeal(meal)
}