package com.darrenfinch.mymealplanner.domain.mealplanform.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.utils.KeyboardUtils
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentMealPlanFormBinding
import com.darrenfinch.mymealplanner.domain.viewmodels.ObservableMealPlan
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

    private fun setupUI() {
        binding.apply {
            doneButton.setOnClickListener { onDoneClicked() }
        }
    }

    override fun bindMealPlan(mealPlan: ObservableMealPlan) {
        binding.mealPlan = mealPlan
    }

    private fun onDoneClicked() {
        KeyboardUtils.hideKeyboardFrom(getContext(), getRootView())

        for (listener in getListeners()) {
            listener.onDoneClicked(getFinalMealPlan())
        }
    }

    private fun getFinalMealPlan(): MealPlan {
        return MealPlan(
            id = binding.mealPlan?.id ?: 0,
            title = binding.mealPlanNameEditText.text.toString(),
            requiredCalories = binding.requiredCaloriesEditText.text.toString().toInt(),
            requiredProtein = binding.requiredProteinEditText.text.toString().toInt(),
            requiredFat = binding.requiredFatEditText.text.toString().toInt(),
            requiredCarbohydrates = binding.requiredCarbohydratesEditText.text.toString().toInt()
        )
    }
}