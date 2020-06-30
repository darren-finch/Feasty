package com.darrenfinch.mymealplanner.domain.allmeals.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darrenfinch.mymealplanner.common.controllers.BaseFragment

class AllMealsFragment : BaseFragment() {

    private lateinit var controller: AllMealsController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewMvc = controllerCompositionRoot.getViewMvcFactory().getAllMealsViewMvc(container)
        controller = AllMealsController(controllerCompositionRoot.getMealsRepository())
        controller.bindView(viewMvc)
        controller.fetchMeals(viewLifecycleOwner)
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
}
