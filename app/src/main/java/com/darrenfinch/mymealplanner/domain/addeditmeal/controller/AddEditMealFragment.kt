package com.darrenfinch.mymealplanner.domain.addeditmeal.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.darrenfinch.mymealplanner.common.controllers.BaseFragment
import com.darrenfinch.mymealplanner.domain.addeditmeal.view.AddEditMealViewMvc
import com.darrenfinch.mymealplanner.domain.addeditmeal.view.AddEditMealViewMvcImpl
import com.darrenfinch.mymealplanner.domain.selectfoodformeal.controller.SelectFoodForMealDialog

class AddEditMealFragment : BaseFragment() {

    private lateinit var controller: AddEditMealController
    private lateinit var viewMvc: AddEditMealViewMvc

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewMvc = controllerCompositionRoot.getViewMvcFactory().getAddEditMealViewMvc(container)
        controller = AddEditMealController(childFragmentManager)
        controller.bindView(viewMvc)
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
