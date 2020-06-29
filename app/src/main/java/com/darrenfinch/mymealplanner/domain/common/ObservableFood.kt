package com.darrenfinch.mymealplanner.domain.common

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.darrenfinch.mymealplanner.BR
import com.darrenfinch.mymealplanner.model.data.Food
import com.darrenfinch.mymealplanner.model.data.MacroNutrients
import com.darrenfinch.mymealplanner.model.data.MetricUnit

class ObservableFood : BaseObservable() {
    var dirty = false
        private set

    init {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                dirty = true
            }
        })
    }

    var id: Int = 0

    @get:Bindable
    var title: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }

    @get:Bindable
    var servingSizeString: String = "0.0"
        set(value) {
            field = value
            notifyPropertyChanged(BR.servingSizeString)
        }

    fun getNumericalServingSize() = servingSizeString.toDouble()

    @get:Bindable
    var servingSizeUnit: MetricUnit = MetricUnit.defaultUnit
        set(value) {
            field = value
            notifyPropertyChanged(BR.servingSizeUnit)
        }

    @get:Bindable
    var caloriesString: String = "0"
        set(value) {
            field = value
            notifyPropertyChanged(BR.caloriesString)
        }

    fun getNumericalCalories() = caloriesString.toInt()

    @get:Bindable
    var carbohydratesString: String = "0"
        set(value) {
            field = value
            notifyPropertyChanged(BR.carbohydratesString)
        }

    fun getNumericalCarbohydrates() = carbohydratesString.toInt()

    @get:Bindable
    var fatString: String = "0"
        set(value) {
            field = value
            notifyPropertyChanged(BR.fatString)
        }

    fun getNumericalFat() = fatString.toInt()

    @get:Bindable
    var proteinString: String = "0"
        set(value) {
            field = value
            notifyPropertyChanged(BR.proteinString)
        }

    fun getNumericalProtein() = proteinString.toInt()

    fun set(food: Food) {
        id = food.id
        title = food.title
        servingSizeUnit = food.servingSizeUnit
        servingSizeString = food.servingSize.toString()
        caloriesString = food.macroNutrients.calories.toString()
        carbohydratesString = food.macroNutrients.carbohydrates.toString()
        fatString = food.macroNutrients.fat.toString()
        proteinString = food.macroNutrients.protein.toString()
    }

    fun get(): Food {
        return Food(
            id = id,
            title = title,
            servingSizeUnit = servingSizeUnit,
            servingSize = getNumericalServingSize(),
            macroNutrients = MacroNutrients(
                calories = getNumericalCalories(),
                carbohydrates = getNumericalCarbohydrates(),
                fat = getNumericalFat(),
                protein = getNumericalProtein()
            )
        )
    }
}