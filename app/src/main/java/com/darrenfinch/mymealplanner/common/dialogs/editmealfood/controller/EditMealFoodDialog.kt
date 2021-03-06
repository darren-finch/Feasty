package com.darrenfinch.mymealplanner.common.dialogs.editmealfood.controller

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import com.darrenfinch.mymealplanner.common.controllers.BaseDialog
import com.darrenfinch.mymealplanner.common.controllers.ControllerSavedState
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.view.EditMealFoodViewMvc
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood

class EditMealFoodDialog : BaseDialog() {
    companion object {
        // Saved state
        const val CONTROLLER_SAVED_STATE = "CONTROLLER_SAVED_STATE"

        // Arguments
        const val MEAL_FOOD_ARG = "MEAL_FOOD_ARG"
        const val INDEX_ARG = "INDEX_ARG"

        fun newInstance(mealFood: UiMealFood, index: Int): EditMealFoodDialog {
            val bundle = Bundle().apply {
                putSerializable(MEAL_FOOD_ARG, mealFood)
                putInt(INDEX_ARG, index)
            }
            val fragment = EditMealFoodDialog()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var controller: EditMealFoodController
    private lateinit var viewMvc: EditMealFoodViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = fragmentCompositionRoot.getEditMealController()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        viewMvc = fragmentCompositionRoot.getViewMvcFactory().getEditMealViewMvc(null)

        setControllerArgs(requireArguments())
        restoreControllerState(savedInstanceState)
        controller.bindView(viewMvc)
        controller.bindViewStateToView()

        return viewMvc.makeDialog()
    }

    private fun setControllerArgs(arguments: Bundle) {
        controller.setArgs(
            arguments.getSerializable(MEAL_FOOD_ARG) as UiMealFood,
            arguments.getInt(INDEX_ARG)
        )
    }

    private fun restoreControllerState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            controller.restoreState(it.getSerializable(CONTROLLER_SAVED_STATE) as ControllerSavedState)
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

    override fun onDestroyView() {
        super.onDestroyView()
        viewMvc.releaseViewRefs()
    }
}