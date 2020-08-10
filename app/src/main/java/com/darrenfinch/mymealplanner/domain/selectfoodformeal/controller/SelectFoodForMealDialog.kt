package com.darrenfinch.mymealplanner.domain.selectfoodformeal.controller

import android.app.Dialog
import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.darrenfinch.mymealplanner.common.controllers.BaseDialog
import com.darrenfinch.mymealplanner.domain.selectfoodformeal.view.SelectFoodForMealViewMvc

class SelectFoodForMealDialog : BaseDialog() {

    private lateinit var controller: SelectFoodForMealController
    private lateinit var viewMvc: SelectFoodForMealViewMvc

    private val args: SelectFoodForMealDialogArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        viewMvc = fragmentCompositionRoot.getViewMvcFactory().getSelectFoodForMealsViewMvc(null)

        controller = fragmentCompositionRoot.getSelectFoodForMealController(args.currentMeal)
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
