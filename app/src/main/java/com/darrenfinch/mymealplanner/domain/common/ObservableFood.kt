package com.darrenfinch.mymealplanner.domain.common

import androidx.core.text.isDigitsOnly
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.databinding.Observable
import com.darrenfinch.mymealplanner.BR
import com.darrenfinch.mymealplanner.domain.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.domain.physicalquantities.units.MeasurementUnit
import com.darrenfinch.mymealplanner.model.data.entities.Food
import com.darrenfinch.mymealplanner.model.data.entitysubdata.MacroNutrients

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

    var servingSize: PhysicalQuantity = PhysicalQuantity.defaultPhysicalQuantity

    @get:Bindable
    var servingSizeQuantity: String = "0.0"
        set(value) {
            //TODO(): Make sure this value is a digit value.
            if(value.isNotEmpty()) {
                field = value
                servingSize = PhysicalQuantity(value.toDouble(), servingSizeUnit)
                notifyPropertyChanged(BR.servingSizeQuantity)
            }
        }

    @get:Bindable
    var servingSizeUnit: MeasurementUnit = MeasurementUnit.defaultUnit
        set(value) {
            field = value
            servingSize = PhysicalQuantity(servingSizeQuantity.toDouble(), value)
            notifyPropertyChanged(BR.servingSizeUnit)
        }


    @get:Bindable
    var caloriesString: String = "0"
        set(value) {
            field = value
            notifyPropertyChanged(BR.caloriesString)
        }

    @get:Bindable
    var carbsString: String = "0"
        set(value) {
            field = value
            notifyPropertyChanged(BR.carbsString)
        }

    @get:Bindable
    var fatString: String = "0"
        set(value) {
            field = value
            notifyPropertyChanged(BR.fatString)
        }

    @get:Bindable
    var proteinString: String = "0"
        set(value) {
            field = value
            notifyPropertyChanged(BR.proteinString)
        }

    fun set(food: Food) {
        id = food.id
        title = food.title
        servingSize = food.servingSize
        servingSizeUnit = food.servingSize.unit
        servingSizeQuantity = food.servingSize.quantity.toString()
        caloriesString = food.macroNutrients.calories.toString()
        carbsString = food.macroNutrients.carbs.toString()
        fatString = food.macroNutrients.fat.toString()
        proteinString = food.macroNutrients.protein.toString()
    }

    fun get(): Food {
        return Food(
            id = id,
            title = title,
            servingSize = servingSize,
            macroNutrients = MacroNutrients(
                calories = caloriesString.toInt(),
                carbs = carbsString.toInt(),
                fat = fatString.toInt(),
                protein = proteinString.toInt()
            )
        )
    }
}