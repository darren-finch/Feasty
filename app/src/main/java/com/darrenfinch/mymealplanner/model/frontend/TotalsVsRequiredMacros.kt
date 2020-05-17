package com.darrenfinch.mymealplanner.model.frontend

import androidx.databinding.ObservableInt

data class TotalsVsRequiredMacros(var totalCalories: ObservableInt,
                                  var totalProtein: ObservableInt,
                                  var totalFat: ObservableInt,
                                  var totalCarbohydrates: ObservableInt,
                                  var requiredCalories: ObservableInt,
                                  var requiredProtein: ObservableInt,
                                  var requiredFat: ObservableInt,
                                  var requiredCarbohydrates: ObservableInt)