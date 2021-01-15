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
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.controller.SelectFoodForMealDialog
import com.darrenfinch.mymealplanner.domain.mealplanform.view.MealPlanFormViewMvc
import com.darrenfinch.mymealplanner.model.data.entities.Meal


class MealPlanFormFragment : BaseFragment() {

    companion object {
        const val MEAL_PLAN_DETAILS = "MEAL_PLAN_DETAILS"

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

        controller.setState(savedInstanceState ?: arguments)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putAll(controller.getState())
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
