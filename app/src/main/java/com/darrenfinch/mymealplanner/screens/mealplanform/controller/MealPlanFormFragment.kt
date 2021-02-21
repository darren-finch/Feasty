package com.darrenfinch.mymealplanner.screens.mealplanform.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.darrenfinch.mymealplanner.common.controllers.BaseFragment
import com.darrenfinch.mymealplanner.common.utils.KeyboardUtils
import com.darrenfinch.mymealplanner.screens.mealplanform.view.MealPlanFormViewMvc


class MealPlanFormFragment : BaseFragment() {

    companion object {
        const val CONTROLLER_SAVED_STATE = "CONTROLLER_SAVED_STATE"

        fun newInstance(): MealPlanFormFragment {
            val bundle = Bundle()
            val fragment = MealPlanFormFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var viewMvc: MealPlanFormViewMvc
    private lateinit var controller: MealPlanFormController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = controllerCompositionRoot.getMealPlanFormController()

        // We need to restore state in onCreate because the controller will lose its state if it's in the back stack.
        restoreControllerState(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewMvc = controllerCompositionRoot.getViewMvcFactory().getMealPlanFormViewMvc(null)

        restoreControllerState(savedInstanceState)
        controller.bindView(viewMvc)
        controller.bindMealDetailsToView()

        return viewMvc.getRootView()
    }

    private fun restoreControllerState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            controller.restoreState(savedInstanceState.getSerializable(CONTROLLER_SAVED_STATE) as MealPlanFormController.SavedState)
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
