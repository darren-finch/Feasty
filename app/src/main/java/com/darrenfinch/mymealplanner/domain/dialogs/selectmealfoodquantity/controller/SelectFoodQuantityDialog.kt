package com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener

import com.darrenfinch.mymealplanner.common.controllers.BaseDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.view.SelectFoodQuantityViewMvc
import com.darrenfinch.mymealplanner.domain.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.model.data.entities.Food

class SelectFoodQuantityDialog : BaseDialog(), SelectFoodQuantityViewMvc.Listener {

    companion object {
        const val TAG = "SelectMealFoodQuantityDialog"

        // Arguments
        const val FOOD_ID = "FOOD_ID"

        // Dialog results
        const val SELECTED_FOOD = "SELECTED_FOOD"
        const val SELECTED_FOOD_QUANTITY = "SELECTED_FOOD_QUANTITY"

        fun newInstance(foodId: Int): SelectFoodQuantityDialog {
            val bundle = Bundle()
            bundle.putInt(FOOD_ID, foodId)
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

        controller.setState(savedInstanceState ?: requireArguments())
        controller.bindView(viewMvc)
        controller.fetchFood(this)

        return viewMvc.makeDialog()
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
        outState.putAll(controller.getState())
    }

    override fun onFoodServingSizeChosen(selectedFood: Food, selectedFoodQuantity: PhysicalQuantity) {
        setFragmentResult(
            TAG,
            bundleOf(SELECTED_FOOD to selectedFood, SELECTED_FOOD_QUANTITY to selectedFoodQuantity)
        )
    }
}
