package com.darrenfinch.mymealplanner.domain.foodform.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs

import com.darrenfinch.mymealplanner.common.controllers.BaseFragment
import com.darrenfinch.mymealplanner.domain.foodform.view.FoodFormViewMvc

class FoodFormFragment : BaseFragment() {

    private val viewModel: FoodFormViewModel by viewModels {
        FoodFormViewModelFactory(args.foodId, fragmentCompositionRoot.getApplication())
    }

    private lateinit var controller: FoodFormController

    private val args: FoodFormFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewMvc = initViewMvc(container)
        initController(viewMvc)

        return viewMvc.getRootView()
    }

    private fun initViewMvc(container: ViewGroup?) = fragmentCompositionRoot.getViewMvcFactory().getFoodFormViewMvc(container, args.foodId < 0)

    private fun initController(viewMvc: FoodFormViewMvc) {
        controller = fragmentCompositionRoot.getAddEditFoodController(viewModel)
        controller.bindView(viewMvc)
        controller.fetchFoodDetailsIfPossibleRebindToViewOtherwise(viewLifecycleOwner)
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
