package com.darrenfinch.mymealplanner.domain.allfoods.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darrenfinch.mymealplanner.common.controllers.BaseFragment

class AllFoodsFragment : BaseFragment() {
    private lateinit var controller: AllFoodsController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewMvc = fragmentCompositionRoot.getViewMvcFactory().getAllFoodsViewMvc(container)
        controller = fragmentCompositionRoot.getAllFoodsController()
        controller.bindView(viewMvc)
        controller.fetchAllFoods(viewLifecycleOwner)
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
