package com.darrenfinch.mymealplanner.screens.selectmealplanmeal.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darrenfinch.mymealplanner.common.controllers.BaseFragment
import com.darrenfinch.mymealplanner.screens.selectmealplanmeal.view.SelectMealPlanMealViewMvc

class SelectMealPlanMealFragment : BaseFragment() {

    companion object {
        // Dialog results
        const val SELECTED_MEAL_RESULT = "SELECTED_MEAL_RESULT"

        fun newInstance(): SelectMealPlanMealFragment {
            val bundle = Bundle()
            val fragment = SelectMealPlanMealFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var controller: SelectMealPlanMealController
    private lateinit var viewMvc: SelectMealPlanMealViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = controllerCompositionRoot.getSelectMealPlanMealController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewMvc = controllerCompositionRoot.getViewMvcFactory().getSelectMealPlanMealViewMvc(null)

        controller.bindView(viewMvc)
        controller.getAllMealsAndBindToView()

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