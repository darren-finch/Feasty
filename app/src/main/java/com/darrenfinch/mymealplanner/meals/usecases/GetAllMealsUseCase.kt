package com.darrenfinch.mymealplanner.meals.usecases

import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.misc.BaseObservable
import com.darrenfinch.mymealplanner.meals.models.domain.Meal
import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.model.room.models.meals.DatabaseMeal

class ObserveAllMealsUseCase(private val repository: MainRepository) : BaseObservable<ObserveAllMealsUseCase.Listener>() {

    private val observer = Observer<List<DatabaseMeal>> {
        notifyFetch(it)
    }

    interface Listener {
        fun onFetch(meals: List<Meal>)
    }

    override fun registerListener(listener: Listener) {
        super.registerListener(listener)
        repository.allMeals.observeForever(observer)
    }

    override fun unregisterListener(listener: Listener) {
        super.unregisterListener(listener)
        repository.allMeals.removeObserver(observer)
    }

    fun notifyFetch(dbMeals: List<DatabaseMeal>) {
        for(listener in getListeners()) {
            listener.onFetch()
        }
    }
}