package com.darrenfinch.mymealplanner.common.utils

import com.darrenfinch.mymealplanner.model.data.Food
import com.darrenfinch.mymealplanner.model.data.MacroNutrients
import com.darrenfinch.mymealplanner.model.data.MetricUnit

object Defaults {
    val defaultFood = Food(
        title = "",
        servingSize = 0.0,
        servingSizeUnit = MetricUnit.defaultUnit,
        macroNutrients = MacroNutrients(0, 0, 0, 0)
    )
}
