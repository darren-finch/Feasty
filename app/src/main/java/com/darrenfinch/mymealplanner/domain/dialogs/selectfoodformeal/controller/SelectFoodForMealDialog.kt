package com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.controller

import android.app.Dialog
import android.os.Bundle
import com.darrenfinch.mymealplanner.common.controllers.BaseDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.view.SelectFoodForMealViewMvc
import com.darrenfinch.mymealplanner.model.data.entities.Meal

//TODO: Convert into generic item selection dialog
class SelectFoodForMealDialog : BaseDialog() {

    companion object {
        const val TAG = "SelectFoodForMealDialog"

        // Dialog results
        const val FOOD_ID = "FOOD_ID"

        fun newInstance(): SelectFoodForMealDialog {
            val bundle = Bundle()
            val fragment = SelectFoodForMealDialog()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var controller: SelectFoodForMealController
    private lateinit var viewMvc: SelectFoodForMealViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = fragmentCompositionRoot.getSelectFoodForMealController(onDialogEventListener)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        viewMvc = fragmentCompositionRoot.getViewMvcFactory().getSelectFoodForMealViewMvc(null)

        controller.setState(savedInstanceState ?: requireArguments())
        controller.bindView(viewMvc)
        controller.fetchAllFoods(this)

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
