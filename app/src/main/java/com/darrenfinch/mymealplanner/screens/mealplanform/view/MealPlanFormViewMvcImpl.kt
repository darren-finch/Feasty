package com.darrenfinch.mymealplanner.screens.mealplanform.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
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
    private var _binding: FragmentMealPlanFormBinding? = DataBindingUtil.inflate(
        inflater,
        R.layout.fragment_meal_plan_form,
        parent,
        false
    )
    private val binding = _binding!!

    init {
        setRootView(binding.root)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            doneButton.setOnClickListener { onDoneClicked() }
            toolbar.setNavigationOnClickListener {
                onNavigateUp()
            }

            mealPlanNameEditText.doOnTextChanged { text, _, _, _ ->
                for (listener in getListeners()) {
                    listener.onTitleChange(text.toString())
                }
            }

            requiredCaloriesEditText.doOnTextChanged { text, _, _, _ ->
                for (listener in getListeners()) {
                    listener.onRequiredCaloriesChange(text.toString().toIntOrNull() ?: 0)
                }
            }

            requiredCarbohydratesEditText.doOnTextChanged { text, _, _, _ ->
                for (listener in getListeners()) {
                    listener.onRequiredCarbohydratesChange(text.toString().toIntOrNull() ?: 0)
                }
            }

            requiredFatEditText.doOnTextChanged { text, _, _, _ ->
                for (listener in getListeners()) {
                    listener.onRequiredFatsChange(text.toString().toIntOrNull() ?: 0)
                }
            }

            requiredProteinEditText.doOnTextChanged { text, _, _, _ ->
                for (listener in getListeners()) {
                    listener.onRequiredProteinsChange(text.toString().toIntOrNull() ?: 0)
                }
            }
        }
    }

    override fun bindMealPlanDetails(mealPlan: UiMealPlan) {
        binding.mealPlan = mealPlan
    }

    private fun onDoneClicked() {
        KeyboardUtils.hideKeyboardFrom(getContext(), getRootView())

        for (listener in getListeners()) {
            listener.onDoneButtonClicked()
        }
    }

    private fun onNavigateUp() {
        KeyboardUtils.hideKeyboardFrom(getContext(), getRootView())

        for (listener in getListeners()) {
            listener.onNavigateUp()
        }
    }

    override fun releaseViewRefs() {
        _binding = null
    }

    override fun showProgressIndication() {
        binding.formInputsGroup.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressIndication() {
        binding.formInputsGroup.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }
}