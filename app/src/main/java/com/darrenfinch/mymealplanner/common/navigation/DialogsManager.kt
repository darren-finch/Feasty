package com.darrenfinch.mymealplanner.common.navigation

import android.os.Bundle
import com.darrenfinch.mymealplanner.common.controllers.BaseDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.controller.SelectFoodForMealDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectFoodQuantityDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.controller.SelectMealPlanMealDialog
import com.ncapdevi.fragnav.FragNavController
import java.util.concurrent.ConcurrentLinkedDeque

class DialogsManager(private val navController: FragNavController) {
    interface OnDialogEventListener {
        fun onDialogDismiss(dialogTag: String)
        fun onDialogFinish(dialogTag: String, results: Bundle)
    }

    private var dialogEventListeners = ConcurrentLinkedDeque<OnDialogEventListener>()

    fun registerListener(listener: OnDialogEventListener) {
        dialogEventListeners.add(listener)
    }
    fun unregisterListener(listener: OnDialogEventListener) {
        dialogEventListeners.remove(listener)
    }

    fun showSelectFoodForMealScreenDialog() {
        val dialog = SelectFoodForMealDialog.newInstance()
        setupDialogListeners(dialog)
        navController.showDialogFragment(dialog)
    }

    fun showSelectFoodQuantityDialog(foodId: Int) {
        val dialog = SelectFoodQuantityDialog.newInstance(foodId)
        setupDialogListeners(dialog)
        navController.showDialogFragment(dialog)
    }

    fun showSelectMealPlanMealDialog() {
        val dialog = SelectMealPlanMealDialog.newInstance()
        setupDialogListeners(dialog)
        navController.showDialogFragment(dialog)
    }

    fun clearDialog() {
        navController.clearDialogFragment()
    }

    private fun setupDialogListeners(dialog: BaseDialog) {
        dialog.onDialogEventListener = object : BaseDialog.OnDialogEventListener {
            override fun onDismiss(dialogTag: String) {
                for(listener in dialogEventListeners) {
                    listener.onDialogDismiss(dialogTag)
                }
            }
            override fun onFinish(dialogTag: String, results: Bundle) {
                for(listener in dialogEventListeners) {
                    listener.onDialogFinish(dialogTag, results)
                }
            }
        }
    }
}