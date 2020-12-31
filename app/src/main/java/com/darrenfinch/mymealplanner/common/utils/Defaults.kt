package com.darrenfinch.mymealplanner.common.utils

import com.darrenfinch.mymealplanner.domain.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.model.data.entities.Food
import com.darrenfinch.mymealplanner.model.data.entities.Meal
import com.darrenfinch.mymealplanner.model.data.entities.MealFood
import com.darrenfinch.mymealplanner.model.data.entitysubdata.MacroNutrients
import com.darrenfinch.mymealplanner.model.data.entitysubdata.MetricUnit

object Defaults {
    val defaultMeal = Meal(
        id = 0,
        title = "Empty Meal",
        foods = emptyList()
    )

    val defaultMealFood =
        MealFood(
            id = 0,
            title = "Empty Meal",
            foodId = 0,
            desiredServingSize = PhysicalQuantity.defaultPhysicalQuantity,
            macroNutrients = MacroNutrients(
                0,
                0,
                0,
                0
            )
        )

    val defaultFood = Food(
        title = "",
        servingSize = PhysicalQuantity.defaultPhysicalQuantity,
        macroNutrients = MacroNutrients(
            0,
            0,
            0,
            0
        )
    )
}
