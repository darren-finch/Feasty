package com.darrenfinch.mymealplanner.domain.observables

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.darrenfinch.mymealplanner.BR
import com.darrenfinch.mymealplanner.model.data.entities.MealPlan

class ObservableMealPlan : BaseObservable() {
    var id = 0

    @get:Bindable
    var title: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }

    @get:Bindable
    var requiredCalories: String = "0"
        set(value) {
            field = value
            notifyPropertyChanged(BR.requiredCalories)
        }

    @get:Bindable
    var requiredProtein: String = "0"
        set(value) {
            field = value
            notifyPropertyChanged(BR.requiredProtein)
        }

    @get:Bindable
    var requiredFat: String = "0"
        set(value) {
            field = value
            notifyPropertyChanged(BR.requiredFat)
        }

    @get:Bindable
    var requiredCarbohydrates: String = "0"
        set(value) {
            field = value
            notifyPropertyChanged(BR.requiredCarbohydrates)
        }

    fun get(): MealPlan {
        //TODO: Make this type-casting safer
        return MealPlan(
            id = id,
            title = title,
            requiredCalories = requiredCalories.toInt(),
            requiredProtein = requiredProtein.toInt(),
            requiredCarbohydrates = requiredCarbohydrates.toInt(),
            requiredFat = requiredFat.toInt()
        )
    }

    fun set(mealPlan: MealPlan) {
        id = mealPlan.id
        title = mealPlan.title
        requiredCalories = mealPlan.requiredCalories.toString()
        requiredProtein = mealPlan.requiredProtein.toString()
        requiredCarbohydrates = mealPlan.requiredCarbohydrates.toString()
        requiredFat = mealPlan.requiredFat.toString()
    }
}