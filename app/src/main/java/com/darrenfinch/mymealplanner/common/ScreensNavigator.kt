package com.darrenfinch.mymealplanner.common

import androidx.navigation.NavController
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.domain.allfoods.controller.AllFoodsFragmentDirections
import com.darrenfinch.mymealplanner.domain.allmeals.controller.AllMealsFragmentDirections
import com.darrenfinch.mymealplanner.domain.selectfoodformeal.controller.SelectFoodForMealDialogDirections
import com.darrenfinch.mymealplanner.domain.selectmealfoodquantity.controller.SelectMealFoodQuantityDialogDirections
import com.darrenfinch.mymealplanner.model.data.MealFood

private const val FOOD_ID = "FOOD_ID"

class ScreensNavigator(private val navigationController: NavController) {
    fun navigateFromAllFoodsScreenToAddEditFoodScreen(foodId: Int) {
        val directions =
            AllFoodsFragmentDirections.actionFoodsFragmentToAddEditFoodFragment(
                foodId
            )
        navigationController.navigate(directions)
    }

    fun navigateFromSelectMealFoodQuantityScreenToAddEditMealScreen(newMealFood: MealFood?) {
        val directions =
            SelectMealFoodQuantityDialogDirections.actionSelectMealFoodQuantityDialogToAddEditMealFragment(
                newMealFood
            )
        navigationController.navigate(directions)
    }

    fun navigateFromAllMealsScreenToSelectFoodForMealScreen() {
        val directions =
            AllMealsFragmentDirections.actionMealsFragmentToAddEditMealFragment(
                null
            )
        navigationController.navigate(directions)
    }

    fun navigateToAllFoodsScreen() {
        navigationController.navigate(R.id.allFoodsFragment)
    }

    fun navigateToSelectFoodForMealScreen() {
        navigationController.navigate(R.id.selectFoodForMealDialog)
    }

    fun navigateFromSelectFoodForMealScreenToSelectFoodQuantityScreen(foodId: Int) {
        val directions =
            SelectFoodForMealDialogDirections.actionSelectFoodForMealDialogToSelectMealFoodQuantityDialog(
                foodId
            )
        navigationController.navigate(directions)
    }
}