package com.darrenfinch.mymealplanner.screens.mealform

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.constants.DefaultModels
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MealFormVmTest {
    private lateinit var SUT: MealFormVm

    private val initialUiMealFood1 = DefaultModels.defaultUiMealFood.copy(title = "initialUiMealFood1")
    private val initialUiMealFood2 = DefaultModels.defaultUiMealFood.copy(title = "initialUiMealFood2")
    private val initialUiMealFoods = listOf(initialUiMealFood1, initialUiMealFood2)

    private val defUiMeal = DefaultModels.defaultUiMeal.copy(foods = initialUiMealFoods)

    private val curMealDetails: UiMeal
        get() = SUT.getMealDetails()

    @BeforeEach
    internal fun setUp() {
        SUT = MealFormVm()

        SUT.bindMealDetails(defUiMeal)
    }

    @Test
    internal fun `addMealFood() converts food to meal food and adds it to meal details`() {
        val selectedUiFood = DefaultModels.defaultUiFood
        val selectedUiMealFood = DefaultModels.defaultUiMealFood.copy(
            id = Constants.NEW_ITEM_ID,
            selectedUiFood.id,
            defUiMeal.id,
            selectedUiFood.title,
            selectedUiFood.servingSize,
            selectedUiFood.servingSize,
            selectedUiFood.macroNutrients
        )

        SUT.addMealFood(selectedUiFood)

        val expectedUiMealFoods = initialUiMealFoods + selectedUiMealFood
        assertEquals(expectedUiMealFoods, curMealDetails.foods)
    }

    @Test
    internal fun `updateMealFood() updates meal food at specified index when index is valid`() {
        val updatedUiMealFood = DefaultModels.defaultUiMealFood.copy(title = "updatedUiMealFood")

        SUT.updateMealFood(updatedUiMealFood, 1)

        val expectedUiMealFoods = listOf(initialUiMealFood1, updatedUiMealFood)
        assertEquals(expectedUiMealFoods, curMealDetails.foods)
    }

    @Test
    internal fun `updateMealFood() doesn't update meal food at specified index when index is invalid`() {
        val updatedUiMealFood = DefaultModels.defaultUiMealFood.copy(title = "updatedUiMealFood")

        SUT.updateMealFood(updatedUiMealFood, -1)

        val expectedUiMealFoods = initialUiMealFoods
        assertEquals(expectedUiMealFoods, curMealDetails.foods)
    }

    @Test
    internal fun `removeMealFood() removes meal food at specified index when index is valid`() {
        SUT.removeMealFood(1)

        val expectedUiMealFoods = listOf(initialUiMealFood1)
        assertEquals(expectedUiMealFoods, curMealDetails.foods)
    }

    @Test
    internal fun `removeMealFood() doesn't remove meal food at specified index when index is invalid`() {
        SUT.removeMealFood(-1)

        val expectedUiMealFoods = initialUiMealFoods
        assertEquals(expectedUiMealFoods, curMealDetails.foods)
    }
}