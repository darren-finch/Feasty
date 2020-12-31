package com.darrenfinch.mymealplanner.common.misc

import androidx.navigation.NavController
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.domain.allfoods.controller.AllFoodsFragmentDirections
import com.darrenfinch.mymealplanner.domain.allmeals.controller.AllMealsFragmentDirections
import com.darrenfinch.mymealplanner.domain.mealform.controller.MealFormFragmentDirections
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.controller.SelectFoodForMealDialogDirections
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectMealFoodQuantityDialogDirections
import com.darrenfinch.mymealplanner.domain.mealplan.controller.MealPlanFragmentDirections
import com.darrenfinch.mymealplanner.model.data.entities.Meal
import com.darrenfinch.mymealplanner.model.data.entities.MealFood

class ScreensNavigator(private val navigationController: NavController) {
    fun navigateFromAllFoodsScreenToFoodFormScreen(foodId: Int) {
        val directions =
            AllFoodsFragmentDirections.actionFoodsFragmentToAddEditFoodFragment(
                foodId
            )
        navigationController.navigate(directions)
    }

    fun navigateFromSelectMealFoodQuantityScreenToMealFormScreen(
        newMealFood: MealFood?,
        currentMeal: Meal
    ) {
        val directions =
            SelectMealFoodQuantityDialogDirections.actionSelectMealFoodQuantityDialogToAddEditMealFragment(
                newMealFood,
                currentMeal,
                Constants.DEFAULT_INVALID_MEAL_ID
            )
        navigationController.navigate(directions)
    }

    fun navigateFromAllMealsScreenToSelectFoodForMealScreen(mealId: Int) {
        val directions =
            AllMealsFragmentDirections.actionMealsFragmentToAddEditMealFragment(
                null,
                null,
                mealId
            )
        navigationController.navigate(directions)
    }

    fun navigateToAllMealsScreen() {
        navigationController.navigate(R.id.allMealsFragment)
    }

    fun navigateToAllFoodsScreen() {
        navigationController.navigate(R.id.allFoodsFragment)
    }

    fun navigateToSelectFoodForMealScreen(currentMealData: Meal) {
        val directions =
            MealFormFragmentDirections.actionAddEditMealFragmentToSelectFoodForMealDialog(
                currentMealData
            )
        navigationController.navigate(directions)
    }

    fun navigateFromSelectFoodForMealScreenToSelectFoodQuantityScreen(
        foodId: Int,
        currentMeal: Meal
    ) {
        val directions =
            SelectFoodForMealDialogDirections.actionSelectFoodForMealDialogToSelectMealFoodQuantityDialog(
                foodId,
                currentMeal
            )
        navigationController.navigate(directions)
    }

    fun navigateFromMealPlanScreenToMealPlanFormScreen() {
        navigationController.navigate(R.id.mealPlanFormFragment)
    }

    fun navigateFromSelectMealPlanMealScreenToMealPlanScreen() {
        navigationController.navigateUp()
    }

    fun navigateFromMealPlanScreenToSelectMealPlanMealScreen(mealPlanId: Int) {
        val directions = MealPlanFragmentDirections.actionMealPlanFragmentToSelectMealPlanMealDialog(mealPlanId)
        navigationController.navigate(directions)
    }
}