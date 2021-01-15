package com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller

import android.app.Dialog
import android.os.Bundle
import androidx.navigation.fragment.navArgs

import com.darrenfinch.mymealplanner.common.controllers.BaseDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.controller.SelectFoodForMealDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.view.SelectMealFoodQuantityViewMvc
import com.darrenfinch.mymealplanner.model.data.entities.Meal

class SelectMealFoodQuantityDialog : BaseDialog() {

    companion object {
        const val TAG = "SelectMealFoodQuantityDialog"

        const val FOOD_ID = "FOOD_ID"
        const val MEAL_ID = "MEAL_ID"

        fun newInstance(foodId: Int, mealId: Int): SelectMealFoodQuantityDialog {
            val bundle = Bundle()
            bundle.putInt(FOOD_ID, foodId)
            bundle.putInt(MEAL_ID, mealId)
            val fragment = SelectMealFoodQuantityDialog()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var controller: SelectMealFoodQuantityController
    private lateinit var viewMvc: SelectMealFoodQuantityViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = fragmentCompositionRoot.getSelectMealFoodQuantityController(onDialogEventListener)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        viewMvc = fragmentCompositionRoot.getViewMvcFactory().getSelectMealFoodQuantityViewMvc(null)

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
}
