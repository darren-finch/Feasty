package com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.controller

import android.app.Dialog
import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.darrenfinch.mymealplanner.common.controllers.BaseDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.view.SelectMealPlanMealViewMvc

class SelectMealPlanMealDialog : BaseDialog() {
    private lateinit var controller: SelectMealPlanMealController
    private lateinit var viewMvc: SelectMealPlanMealViewMvc

    private val args: SelectMealPlanMealDialogArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        viewMvc = fragmentCompositionRoot.getViewMvcFactory().getSelectMealPlanMealViewMvc(null)

        controller = fragmentCompositionRoot.getSelectMealPlanMealController(args.mealPlanId)
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
}