package com.darrenfinch.mymealplanner.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.darrenfinch.mymealplanner.di.AppModule
import com.darrenfinch.mymealplanner.di.DaggerApplicationComponent
import com.darrenfinch.mymealplanner.di.RoomModule
import com.darrenfinch.mymealplanner.model.FoodsRepository
import com.darrenfinch.mymealplanner.model.data.Food
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectFoodForMealViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var repo: FoodsRepository

    init {
        DaggerApplicationComponent.builder()
            .appModule(AppModule(application))
            .roomModule(RoomModule(application))
            .build()
            .inject(this)
    }

    private val allFoodsLiveData: MutableLiveData<List<Food>> = MutableLiveData()

    fun fetchAllFoods(): LiveData<List<Food>> {
        viewModelScope.launch {
            allFoodsLiveData.postValue(repo.getFoods())
        }
        return allFoodsLiveData
    }
}