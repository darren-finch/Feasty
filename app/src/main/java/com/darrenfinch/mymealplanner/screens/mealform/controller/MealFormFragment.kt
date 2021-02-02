package com.darrenfinch.mymealplanner.screens.mealform.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.darrenfinch.mymealplanner.common.controllers.BaseFragment
import com.darrenfinch.mymealplanner.screens.mealform.view.MealFormViewMvc

class MealFormFragment : BaseFragment() {

    companion object {

        // Arguments
        const val MEAL_ID_ARG = "MEAL_ID_ARG"

        // State
        const val CONTROLLER_SAVED_STATE = "CONTROLLER_SAVED_STATE"

        fun newInstance(mealId: Int): MealFormFragment {
            val bundle = Bundle()
            bundle.putInt(MEAL_ID_ARG, mealId)
            val fragment = MealFormFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var controller: MealFormController
    private lateinit var viewMvc: MealFormViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = controllerCompositionRoot.getMealFormController()

        // We need to restore state in onCreate because the controller will lose its state if it's in the back stack.
        restoreControllerState(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewMvc = controllerCompositionRoot.getViewMvcFactory().getMealFormViewMvc(container)

        controller.bindView(viewMvc)
        setControllerArgs(requireArguments())
        restoreControllerState(savedInstanceState)
        controller.getMealDetails()

        return viewMvc.getRootView()
    }

    private fun setControllerArgs(arguments: Bundle) {
        controller.setArgs(arguments.getInt(MEAL_ID_ARG))
    }

    private fun restoreControllerState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            controller.restoreState(savedInstanceState.getSerializable(CONTROLLER_SAVED_STATE) as MealFormControllerImpl.SavedState)
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
