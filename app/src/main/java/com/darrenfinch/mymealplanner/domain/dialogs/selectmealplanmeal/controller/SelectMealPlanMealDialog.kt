package com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.controller

import android.app.Dialog
import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.darrenfinch.mymealplanner.common.controllers.BaseDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.controller.SelectFoodForMealDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.view.SelectMealPlanMealViewMvc
import com.darrenfinch.mymealplanner.model.data.entities.Meal

class SelectMealPlanMealDialog : BaseDialog(), SelectMealPlanMealViewMvc.Listener {

    companion object {
        const val TAG = "SelectMealPlanMealDialog"

        const val MEAL_PLAN_ID = "MEAL_PLAN_ID"

        fun newInstance(mealPlanId: Int): SelectMealPlanMealDialog {
            val bundle = Bundle()
            bundle.putInt(MEAL_PLAN_ID, mealPlanId)
            val fragment = SelectMealPlanMealDialog()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var controller: SelectMealPlanMealController
    private lateinit var viewMvc: SelectMealPlanMealViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = fragmentCompositionRoot.getSelectMealPlanMealController()
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
        viewMvc.registerListener(this)
    }

    override fun onStop() {
        super.onStop()
        controller.onStop()
        viewMvc.unregisterListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putAll(controller.getState())
    }

    override fun onMealSelected(meal: Meal) {
        dismiss()
    }
}