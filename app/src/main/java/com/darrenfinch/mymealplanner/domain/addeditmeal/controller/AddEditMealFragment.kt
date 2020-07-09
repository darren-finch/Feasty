package com.darrenfinch.mymealplanner.domain.addeditmeal.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.darrenfinch.mymealplanner.common.controllers.BaseFragment
import com.darrenfinch.mymealplanner.domain.addeditmeal.CurrentlyEditedMealViewModel
import com.darrenfinch.mymealplanner.domain.addeditmeal.view.AddEditMealViewMvc

class AddEditMealFragment : BaseFragment() {

    private val args: AddEditMealFragmentArgs by navArgs()

    private lateinit var controller: AddEditMealController
    private lateinit var viewMvc: AddEditMealViewMvc

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewMvc = fragmentCompositionRoot.getViewMvcFactory().getAddEditMealViewMvc(container)
        controller = fragmentCompositionRoot.getAddEditMealController()
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
