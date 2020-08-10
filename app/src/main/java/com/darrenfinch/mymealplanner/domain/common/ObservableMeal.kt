package com.darrenfinch.mymealplanner.domain.common

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.darrenfinch.mymealplanner.BR
import com.darrenfinch.mymealplanner.model.data.Meal
import com.darrenfinch.mymealplanner.model.data.MealFood

class ObservableMeal : BaseObservable() {

    var id = 0

    @get:Bindable
    var title: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }

    var foods: List<MealFood> = mutableListOf()

    fun get(): Meal {
        return Meal(id = id, title = title, foods = foods)
    }
    fun set(meal: Meal) {
        id = meal.id
        title = meal.title
        foods = meal.foods
    }
}