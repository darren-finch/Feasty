package com.darrenfinch.mymealplanner.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.darrenfinch.mymealplanner.di.AppModule
import com.darrenfinch.mymealplanner.di.DaggerApplicationComponent
import com.darrenfinch.mymealplanner.di.RoomModule
import com.darrenfinch.mymealplanner.model.FoodsRepository
import com.darrenfinch.mymealplanner.model.room.Food
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddEditFoodViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var repo: FoodsRepository

    init {
        DaggerApplicationComponent.builder()
            .appModule(AppModule(application))
            .roomModule(RoomModule(application))
            .build()
            .inject(this)
    }

    fun fetchFood(foodId: Int) = repo.fetchFood(foodId)
    fun insertFood(food: Food) = repo.insertFood(food)
    fun updateFood(food: Food) = repo.updateFood(food)
}
