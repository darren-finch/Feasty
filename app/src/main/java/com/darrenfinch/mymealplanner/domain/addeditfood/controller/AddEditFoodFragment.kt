package com.darrenfinch.mymealplanner.domain.addeditfood.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs

import com.darrenfinch.mymealplanner.common.controllers.BaseFragment
import com.darrenfinch.mymealplanner.domain.addeditfood.view.AddEditFoodViewMvc

class AddEditFoodFragment : BaseFragment() {

    private val viewModel: AddEditFoodViewModel by viewModels {
        AddEditFoodViewModelFactory(args.foodId, controllerCompositionRoot.getApplication())
    }

    private lateinit var controller: AddEditFoodController

    private val args: AddEditFoodFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewMvc = initViewMvc(container)
        initController(viewMvc)

        return viewMvc.getRootView()
    }

    private fun initViewMvc(container: ViewGroup?) = controllerCompositionRoot.getViewMvcFactory().getAddEditFoodViewMvc(container, args.foodId < 0)

    private fun initController(viewMvc: AddEditFoodViewMvc) {
        controller = controllerCompositionRoot.getAddEditFoodController(viewModel)
        controller.bindView(viewMvc)
        controller.fetchFoodDetailsIfPossibleRebindToViewMvcOtherwise(viewLifecycleOwner)
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