package com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.controller

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf

import com.darrenfinch.mymealplanner.common.controllers.BaseDialog
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.view.SelectFoodQuantityViewMvc

class SelectFoodQuantityDialog : BaseDialog() {

    companion object {
        const val TAG = "SelectMealFoodQuantityDialog"

        // Arguments
        const val FOOD_ID_ARG = "FOOD_ID_ARG"

        // State
        const val CONTROLLER_SAVED_STATE = "CONTROLLER_SAVED_STATE"

        // Dialog results
        const val FOOD_ID_RESULT = "FOOD_ID_RESULT"
        const val SELECTED_FOOD_RESULT = "SELECTED_FOOD_RESULT"
        const val SELECTED_FOOD_QUANTITY_RESULT = "SELECTED_FOOD_QUANTITY_RESULT"

        fun newInstance(foodId: Int): SelectFoodQuantityDialog {
            val bundle = Bundle()
            bundle.putInt(FOOD_ID_ARG, foodId)
            val fragment = SelectFoodQuantityDialog()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var controller: SelectFoodQuantityController
    private lateinit var viewMvc: SelectFoodQuantityViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = controllerCompositionRoot.getSelectMealFoodQuantityController()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        viewMvc = controllerCompositionRoot.getViewMvcFactory().getSelectMealFoodQuantityViewMvc(null)

        setControllerArgs(requireArguments())
        restoreControllerState(savedInstanceState)
        controller.bindView(viewMvc)
        controller.getFoodAndBindToView()

        return viewMvc.makeDialog()
    }

    private fun setControllerArgs(arguments: Bundle) {
        controller.setArgs(arguments.getInt(FOOD_ID_ARG))
    }

    private fun restoreControllerState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            controller.restoreState(it.getSerializable(CONTROLLER_SAVED_STATE) as SelectFoodQuantityController.SavedState)
        }
    }

    override fun onStart() {
        super.onStart()
        controller.onStart()
    }

    override fun onStop() {
        super.onStop()
        controller.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putAll(bundleOf(CONTROLLER_SAVED_STATE to controller.getState()))
    }
}
