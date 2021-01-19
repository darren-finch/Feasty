package com.darrenfinch.mymealplanner.domain.mealplanform.controller

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import com.darrenfinch.mymealplanner.common.controllers.BaseFragment
import com.darrenfinch.mymealplanner.domain.mealplanform.view.MealPlanFormViewMvc


class MealPlanFormFragment : BaseFragment() {

    companion object {
        const val CONTROLLER_SAVED_STATE = "CONTROLLER_SAVED_STATE"

        fun newInstance(): MealPlanFormFragment {
            val bundle = Bundle()
            val fragment = MealPlanFormFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var viewMvc: MealPlanFormViewMvc
    private lateinit var controller: MealPlanFormController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = fragmentCompositionRoot.getMealPlanFormController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewMvc = fragmentCompositionRoot.getViewMvcFactory().getMealPlanFormViewMvc(null)

        restoreControllerState(savedInstanceState)
        controller.bindView(viewMvc)

        return viewMvc.getRootView()
    }

    private fun restoreControllerState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            controller.restoreState(savedInstanceState.getSerializable(CONTROLLER_SAVED_STATE) as MealPlanFormController.SavedState)
        }
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putAll(bundleOf(CONTROLLER_SAVED_STATE to controller.getState()))
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
