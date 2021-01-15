package com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.controller

import android.app.Dialog
import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.darrenfinch.mymealplanner.common.controllers.BaseDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.controller.SelectFoodForMealDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.view.SelectMealPlanMealViewMvc
import com.darrenfinch.mymealplanner.model.data.entities.Meal

class SelectMealPlanMealDialog : BaseDialog() {

    companion object {
        const val TAG = "SelectMealPlanMealDialog"

        // Dialog results
        const val SELECTED_MEAL = "SELECTED_MEAL"

        fun newInstance(): SelectMealPlanMealDialog {
            val bundle = Bundle()
            val fragment = SelectMealPlanMealDialog()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var controller: SelectMealPlanMealController
    private lateinit var viewMvc: SelectMealPlanMealViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = fragmentCompositionRoot.getSelectMealPlanMealController(onDialogEventListener)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        viewMvc = fragmentCompositionRoot.getViewMvcFactory().getSelectMealPlanMealViewMvc(null)

        controller.setState(savedInstanceState ?: requireArguments())
        controller.bindView(viewMvc)
        controller.fetchMeals(this)

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