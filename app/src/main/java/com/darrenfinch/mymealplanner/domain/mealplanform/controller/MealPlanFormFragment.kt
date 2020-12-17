package com.darrenfinch.mymealplanner.domain.mealplanform.controller

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.darrenfinch.mymealplanner.common.controllers.BaseFragment
import com.darrenfinch.mymealplanner.domain.mealplanform.view.MealPlanFormViewMvc


class MealPlanFormFragment : BaseFragment() {
    private val viewModel: MealPlanFormViewModel by viewModels {
        ViewModelProvider.NewInstanceFactory()
    }

    private lateinit var viewMvc: MealPlanFormViewMvc
    private lateinit var controller: MealPlanFormController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewMvc = fragmentCompositionRoot.getViewMvcFactory().getMealPlanFormViewMvc(null)

        controller = fragmentCompositionRoot.getMealPlanFormController(viewModel)
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
        hideKeyboardFrom(requireContext(), requireView())
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}