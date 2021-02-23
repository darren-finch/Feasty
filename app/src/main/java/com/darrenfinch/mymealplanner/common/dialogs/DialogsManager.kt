package com.darrenfinch.mymealplanner.common.dialogs

import android.content.Context
import androidx.annotation.StringRes
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.controller.EditMealFoodDialog
import com.darrenfinch.mymealplanner.common.dialogs.prompt.controller.PromptDialog
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood
import com.ncapdevi.fragnav.FragNavController

class DialogsManager(private val context: Context, private val navController: FragNavController) {
    fun showEditMealFoodDialog(mealFood: UiMealFood, index: Int) {
        val dialog = EditMealFoodDialog.newInstance(mealFood, index)
        navController.showDialogFragment(dialog)
    }

    fun showDeleteFoodConfirmationDialog() {
        val dialog = PromptDialog.newInstance(
            title = getString(R.string.are_you_sure),
            message = getString(R.string.are_you_sure_you_want_to_delete_this_food),
            positiveButtonCaption = getString(android.R.string.ok),
            negativeButtonCaption = getString(android.R.string.cancel),
        )
        navController.showDialogFragment(dialog)
    }

    fun showDeleteMealConfirmationDialog() {
        val dialog = PromptDialog.newInstance(
            title = getString(R.string.are_you_sure),
            message = getString(R.string.are_you_sure_you_want_to_delete_this_meal),
            positiveButtonCaption = getString(android.R.string.ok),
            negativeButtonCaption = getString(android.R.string.cancel),
        )
        navController.showDialogFragment(dialog)
    }

    fun getString(@StringRes resId: Int) = context.getString(resId)

    fun clearDialog() {
        navController.clearDialogFragment()
    }
}