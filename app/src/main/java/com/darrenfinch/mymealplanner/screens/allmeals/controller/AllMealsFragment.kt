package com.darrenfinch.mymealplanner.screens.allmeals.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darrenfinch.mymealplanner.common.controllers.BaseFragment

class AllMealsFragment : BaseFragment() {

    companion object {
        fun newInstance() = AllMealsFragment()
    }

    private lateinit var controller: AllMealsController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewMvc = controllerCompositionRoot.getViewMvcFactory().getAllMealsViewMvc(container)
        controller = controllerCompositionRoot.getAllMealsController()
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
}
