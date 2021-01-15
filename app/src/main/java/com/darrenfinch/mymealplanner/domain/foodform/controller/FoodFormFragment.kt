package com.darrenfinch.mymealplanner.domain.foodform.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.darrenfinch.mymealplanner.common.controllers.BaseFragment
import com.darrenfinch.mymealplanner.common.navigation.BackPressListener

class FoodFormFragment : BaseFragment() {

    companion object {
        const val FOOD_ID = "FOOD_ID"
        const val FOOD_DETAILS = "FOOD_DETAILS"
        const val HAS_LOADED_FOOD_DETAILS = "HAS_LOADED_FOOD_DETAILS"

        fun newInstance(foodId: Int): FoodFormFragment {
            val bundle = Bundle()
            bundle.putInt(FOOD_ID, foodId)
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
        controller.setState(savedInstanceState ?: requireArguments())
        controller.fetchFoodDetailsIfPossibleRebindToViewOtherwise(viewLifecycleOwner)

        return viewMvc.getRootView()
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
        outState.putAll(controller.getState())
    }
}
