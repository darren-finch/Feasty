package com.darrenfinch.mymealplanner.domain.selectfoodformeal.controller

import android.app.Dialog
import android.os.Bundle
import com.darrenfinch.mymealplanner.common.dialogs.BaseDialog
import com.darrenfinch.mymealplanner.domain.selectfoodformeal.view.SelectFoodForMealViewMvc

class SelectFoodForMealDialog : BaseDialog() {

    private lateinit var viewMvc: SelectFoodForMealViewMvc

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        viewMvc = controllerCompositionRoot.getViewMvcFactory().getSelectFoodForMealsViewMvc(null)
        return viewMvc.makeDialog()
    }
}
