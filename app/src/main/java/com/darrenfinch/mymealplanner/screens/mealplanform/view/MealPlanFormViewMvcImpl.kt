package com.darrenfinch.mymealplanner.screens.mealplanform.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.utils.KeyboardUtils
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentMealPlanFormBinding
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan

class MealPlanFormViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : BaseObservableViewMvc<MealPlanFormViewMvc.Listener>(), MealPlanFormViewMvc {
    private val binding: FragmentMealPlanFormBinding = DataBindingUtil.inflate(
        inflater,
        R.layout.fragment_meal_plan_form,
        parent,
        false
    )

    init {
        setRootView(binding.root)
        setupUI()
    }

    override fun bindMealPlanDetails(mealPlan: UiMealPlan) {
        binding.mealPlan = mealPlan
    }

    override fun getMealPlanDetails(): UiMealPlan {
        return UiMealPlan(
            id = binding.mealPlan?.id ?: 0,
            title = binding.mealPlanNameEditText.text.toString(),
            requiredCalories = binding.requiredCaloriesEditText.text.toString().toInt(),
            requiredProteins = binding.requiredProteinEditText.text.toString().toInt(),
            requiredFats = binding.requiredFatEditText.text.toString().toInt(),
            requiredCarbohydrates = binding.requiredCarbohydratesEditText.text.toString().toInt()
        )
    }

    override fun showProgressIndication() {
        binding.formInputsGroup.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressIndication() {
        binding.formInputsGroup.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun setupUI() {
        binding.apply {
            doneButton.setOnClickListener { onDoneClicked() }
        }
    }

    private fun onDoneClicked() {
        KeyboardUtils.hideKeyboardFrom(getContext(), getRootView())

        for (listener in getListeners()) {
            listener.onDoneButtonClicked(getMealPlanDetails())
        }
    }
}