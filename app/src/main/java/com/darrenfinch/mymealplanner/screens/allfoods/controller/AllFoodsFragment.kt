package com.darrenfinch.mymealplanner.screens.allfoods.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darrenfinch.mymealplanner.common.controllers.BaseFragment
import com.darrenfinch.mymealplanner.screens.allfoods.view.AllFoodsViewMvc

class AllFoodsFragment : BaseFragment() {

    companion object {
        fun newInstance() = AllFoodsFragment()
    }

    private lateinit var controller: AllFoodsController
    private lateinit var viewMvc: AllFoodsViewMvc

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewMvc = controllerCompositionRoot.getViewMvcFactory().getAllFoodsViewMvc(container)
        controller = controllerCompositionRoot.getAllFoodsController()
        controller.bindView(viewMvc)
        controller.getAllFoodsAndBindToView()
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
