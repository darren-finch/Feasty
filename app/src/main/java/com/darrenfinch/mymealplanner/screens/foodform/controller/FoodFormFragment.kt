package com.darrenfinch.mymealplanner.screens.foodform.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf

import com.darrenfinch.mymealplanner.common.controllers.BaseFragment
import com.darrenfinch.mymealplanner.screens.foodform.view.FoodFormViewMvc

class FoodFormFragment : BaseFragment() {

    companion object {
        const val FOOD_ID_ARG = "FOOD_ID_ARG"
        const val CONTROLLER_SAVED_STATE = "CONTROLLER_SAVED_STATE"

        fun newInstance(foodId: Int): FoodFormFragment {
            val bundle = Bundle()
            bundle.putInt(FOOD_ID_ARG, foodId)
            val fragment = FoodFormFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var controller: FoodFormController
    private lateinit var viewMvc: FoodFormViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = controllerCompositionRoot.getFoodFormController()

        // We need to restore state in onCreate because the controller will lose its state if it's in the back stack.
        restoreControllerState(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewMvc = controllerCompositionRoot.getViewMvcFactory().getFoodFormViewMvc(container)

        controller.bindView(viewMvc)
        setControllerArgs(requireArguments())
        restoreControllerState(savedInstanceState)
        controller.getFoodDetails()

        return viewMvc.getRootView()
    }

    private fun setControllerArgs(arguments: Bundle) {
        controller.setArgs(arguments.getInt(FOOD_ID_ARG))
    }

    private fun restoreControllerState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            controller.restoreState(savedInstanceState.getSerializable(CONTROLLER_SAVED_STATE) as FoodFormControllerImpl.SavedState)
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
