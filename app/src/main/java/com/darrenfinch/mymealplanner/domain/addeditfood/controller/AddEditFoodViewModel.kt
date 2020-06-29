package com.darrenfinch.mymealplanner.domain.addeditfood.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.di.AppModule
import com.darrenfinch.mymealplanner.common.di.DaggerApplicationComponent
import com.darrenfinch.mymealplanner.common.di.RoomModule
import com.darrenfinch.mymealplanner.domain.common.ObservableFood
import com.darrenfinch.mymealplanner.model.FoodsRepository
import com.darrenfinch.mymealplanner.model.data.Food
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

    private val observableFood = ObservableFood()
    fun getObservableFood() = observableFood

    fun fetchFood(foodId: Int, viewLifecycleOwner: LifecycleOwner) {
        repo.fetchFood(foodId).observe(viewLifecycleOwner, Observer { food ->
            observableFood.set(food)
        })
    }
    fun insertFood(food: Food) = repo.insertFood(food)
    fun updateFood(food: Food) = repo.updateFood(food)
}
