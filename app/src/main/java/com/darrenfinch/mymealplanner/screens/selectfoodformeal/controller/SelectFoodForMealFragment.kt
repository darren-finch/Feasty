package com.darrenfinch.mymealplanner.screens.selectfoodformeal.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darrenfinch.mymealplanner.common.controllers.BaseFragment
import com.darrenfinch.mymealplanner.common.logs.getClassTag
import com.darrenfinch.mymealplanner.screens.selectfoodformeal.view.SelectFoodForMealViewMvc

class SelectFoodForMealFragment : BaseFragment() {
    companion object {
        val ASYNC_COMPLETION_TOKEN = getClassTag()

        // Dialog results
        const val SELECTED_FOOD_RESULT = "SELECTED_FOOD_RESULT"

        fun newInstance(): SelectFoodForMealFragment {
            val bundle = Bundle()
            val fragment = SelectFoodForMealFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var controller: SelectFoodForMealController
    private lateinit var viewMvc: SelectFoodForMealViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = controllerCompositionRoot.getSelectFoodForMealController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewMvc = controllerCompositionRoot.getViewMvcFactory().getSelectFoodForMealViewMvc(null)

        controller.bindView(viewMvc)
        controller.getFoods()

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

    override fun onDestroyView() {
        super.onDestroyView()
        viewMvc.releaseViewRefs()
    }
}
