package com.darrenfinch.mymealplanner.common.dialogs

import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.controller.SelectFoodForMealDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodquantity.controller.SelectFoodQuantityDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.controller.SelectMealPlanMealDialog
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

    fun clearDialog() {
        navController.clearDialogFragment()
    }
}