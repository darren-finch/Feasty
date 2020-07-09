package com.darrenfinch.mymealplanner.domain.addeditmeal

import androidx.lifecycle.ViewModel
import com.darrenfinch.mymealplanner.domain.common.ObservableMeal
import com.darrenfinch.mymealplanner.model.data.Meal

class CurrentlyEditedMealViewModel : ViewModel() {
    var currentlyEditedMeal = ObservableMeal()
}