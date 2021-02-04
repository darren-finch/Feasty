package com.darrenfinch.mymealplanner.common.dialogs

import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.controller.EditMealFoodDialog
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodformeal.controller.SelectFoodForMealDialog
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.controller.SelectFoodQuantityDialog
import com.darrenfinch.mymealplanner.common.dialogs.selectmealplanmeal.controller.SelectMealPlanMealDialog
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood
import com.ncapdevi.fragnav.FragNavController

class DialogsManager(private val navController: FragNavController) {
    fun showSelectFoodForMealScreenDialog() {
        val dialog = SelectFoodForMealDialog.newInstance()
        navController.showDialogFragment(dialog)
    }

    fun showSelectFoodQuantityDialog(foodId: Int) {
        val dialog = SelectFoodQuantityDialog.newInstance(foodId)
        navController.showDialogFragment(dialog)
    }

    fun showSelectMealPlanMealDialog() {
        val dialog = SelectMealPlanMealDialog.newInstance()
        navController.showDialogFragment(dialog)
    }

    fun showEditMealFoodDialog(mealFood: UiMealFood) {
        val dialog = EditMealFoodDialog.newInstance(mealFood)
        navController.showDialogFragment(dialog)
    }

    fun clearDialog() {
        navController.clearDialogFragment()
    }
}