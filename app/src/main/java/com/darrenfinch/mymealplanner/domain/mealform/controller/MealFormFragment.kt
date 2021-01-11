package com.darrenfinch.mymealplanner.domain.mealform.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.darrenfinch.mymealplanner.common.controllers.BaseFragment
import com.darrenfinch.mymealplanner.domain.mealform.view.MealFormViewMvc

class MealFormFragment : BaseFragment() {

    private val viewModel: MealFormViewModel by viewModels {
        ViewModelProvider.NewInstanceFactory()
    }

    private val args: MealFormFragmentArgs by navArgs()

    private lateinit var controller: MealFormController
    private lateinit var viewMvc: MealFormViewMvc

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewMvc = fragmentCompositionRoot.getViewMvcFactory().getMealFormViewMvc(container)
        controller = fragmentCompositionRoot.getMealFormController(
            viewModel,
            args.newMealFood,
            args.currentMeal,
            args.mealId
        )
        controller.bindView(viewMvc)

        return viewMvc.getRootView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller.onViewCreated(viewLifecycleOwner)
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
