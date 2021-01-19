package com.darrenfinch.mymealplanner.domain.mealplanform.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.utils.KeyboardUtils
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentMealPlanFormBinding
import com.darrenfinch.mymealplanner.model.data.entities.MealPlan

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

    override fun bindMealPlanDetails(mealPlan: MealPlan) {
        binding.mealPlan = mealPlan
    }

    override fun getMealPlanDetails(): MealPlan {
        return MealPlan(
            id = binding.mealPlan?.id ?: 0,
            title = binding.mealPlanNameEditText.text.toString(),
            requiredCalories = binding.requiredCaloriesEditText.text.toString().toInt(),
            requiredProteins = binding.requiredProteinEditText.text.toString().toInt(),
            requiredFats = binding.requiredFatEditText.text.toString().toInt(),
            requiredCarbohydrates = binding.requiredCarbohydratesEditText.text.toString().toInt()
        )
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