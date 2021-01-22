package com.darrenfinch.mymealplanner.common.dialogs.selectmealplanmeal.controller

import android.app.Dialog
import android.os.Bundle
import com.darrenfinch.mymealplanner.common.controllers.BaseDialog
import com.darrenfinch.mymealplanner.common.dialogs.selectmealplanmeal.view.SelectMealPlanMealViewMvc

class SelectMealPlanMealDialog : BaseDialog() {

    companion object {
        const val TAG = "SelectMealPlanMealDialog"

        // Dialog results
        const val SELECTED_MEAL_RESULT = "SELECTED_MEAL_RESULT"

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
        controller = controllerCompositionRoot.getSelectMealPlanMealController()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        viewMvc = controllerCompositionRoot.getViewMvcFactory().getSelectMealPlanMealViewMvc(null)

        controller.bindView(viewMvc)
        controller.getAllMealsAndBindToView()

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
}