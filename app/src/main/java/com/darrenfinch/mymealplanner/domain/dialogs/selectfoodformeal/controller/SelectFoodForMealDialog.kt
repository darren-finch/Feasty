package com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.controller

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import com.darrenfinch.mymealplanner.common.controllers.BaseDialog
import com.darrenfinch.mymealplanner.common.dialogs.DialogResult
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.view.SelectFoodForMealViewMvc
import com.darrenfinch.mymealplanner.model.data.entities.Food

//TODO: Convert into generic item selection dialog
class SelectFoodForMealDialog : BaseDialog() {
    companion object {
        const val TAG = "SelectFoodForMealDialog"

        // Dialog results
        const val FOOD_ID_RESULT = "FOOD_ID_RESULT"

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
        controller = controllerCompositionRoot.getSelectFoodForMealController()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        viewMvc = controllerCompositionRoot.getViewMvcFactory().getSelectFoodForMealViewMvc(null)

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
}
