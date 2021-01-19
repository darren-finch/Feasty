package com.darrenfinch.mymealplanner.domain.foodform.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf

import com.darrenfinch.mymealplanner.common.controllers.BaseFragment

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = fragmentCompositionRoot.getFoodFormController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewMvc = fragmentCompositionRoot.getViewMvcFactory().getFoodFormViewMvc(container)

        controller.bindView(viewMvc)
        setControllerArgs(requireArguments())
        restoreControllerState(savedInstanceState)
        controller.fetchFoodDetailsIfPossibleRebindToViewOtherwise(viewLifecycleOwner)

        return viewMvc.getRootView()
    }

    private fun setControllerArgs(arguments: Bundle) {
        controller.setArgs(arguments.getInt(FOOD_ID_ARG))
    }

    private fun restoreControllerState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            controller.restoreState(savedInstanceState.getSerializable(CONTROLLER_SAVED_STATE) as FoodFormController.SavedState)
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
}
