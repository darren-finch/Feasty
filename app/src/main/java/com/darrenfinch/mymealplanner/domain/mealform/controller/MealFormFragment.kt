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
            args.currentMeal
        )
        controller.bindView(viewMvc)

        println("onCreateView was called")
        return viewMvc.getRootView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller.onViewCreated(args.mealId, viewLifecycleOwner)
        println("onViewCreated was called")
    }

    override fun onStart() {
        super.onStart()
        controller.onStart()
        println("onStart was called")
    }

    override fun onResume() {
        super.onResume()
        println("onResume was called")
    }

    override fun onStop() {
        super.onStop()
        controller.onStop()
        println("onStop was called")
    }

    override fun onPause() {
        super.onPause()
        println("onPause was called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("onDestroyView was called")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy was called")
    }

    override fun onDetach() {
        super.onDetach()
        println("onDetach was called")
    }
}
