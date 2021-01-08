package com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller

import android.app.Dialog
import android.os.Bundle
import androidx.navigation.fragment.navArgs

import com.darrenfinch.mymealplanner.common.controllers.BaseDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.view.SelectMealFoodQuantityViewMvc

class SelectMealFoodQuantityDialog : BaseDialog() {

    private lateinit var controller: SelectMealFoodQuantityController
    private lateinit var viewMvc: SelectMealFoodQuantityViewMvc

    private val args: SelectMealFoodQuantityDialogArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        viewMvc = fragmentCompositionRoot.getViewMvcFactory().getSelectMealFoodQuantityViewMvc(null, args.currentMeal.id)

        controller = fragmentCompositionRoot.getSelectMealFoodQuantityController(
            args.foodId,
            args.currentMeal
        )
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

}
