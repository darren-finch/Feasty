package com.darrenfinch.mymealplanner.screens.mealplan

import com.darrenfinch.mymealplanner.screens.mealplan.reactivetest.GetAllMealPlansUseCaseReactive
import com.darrenfinch.mymealplanner.screens.mealplan.reactivetest.MealPlanControllerReactive
import io.mockk.mockk

internal class MealPlanControllerReactiveTest {
    private val getAllMealPlansUseCaseReactive = mockk<GetAllMealPlansUseCaseReactive>()

    private lateinit var SUT: MealPlanControllerReactive


}