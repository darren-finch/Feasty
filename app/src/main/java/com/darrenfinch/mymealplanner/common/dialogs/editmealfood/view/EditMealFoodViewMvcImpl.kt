package com.darrenfinch.mymealplanner.common.dialogs.editmealfood.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.DialogEditMealFoodBinding
import com.darrenfinch.mymealplanner.foods.models.domain.MacroNutrients
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity

class EditMealFoodViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : EditMealFoodViewMvc, BaseObservableViewMvc<EditMealFoodViewMvc.Listener>() {
    private var _binding: DialogEditMealFoodBinding? =
        DataBindingUtil.inflate(inflater, R.layout.dialog_edit_meal_food, parent, false)
    private val binding = _binding!!

    init {
        setRootView(binding.root)
        setupUi()
    }

    private fun setupUi() {
        binding.apply {
            foodQuantityEditText.doOnTextChanged { text, _, _, _ ->
                for (listener in getListeners()) {
                    listener.onMealFoodServingSizeQuantityChange(text.toString().toDoubleOrNull() ?: 0.0)
                }
            }
        }
    }

    override fun bindMealFoodTitle(title: String) {
        binding.foodNameTextView.text = title
    }

    @SuppressLint("SetTextI18n")
    override fun bindMealFoodMacros(macroNutrients: MacroNutrients) {
        binding.mealFoodMacrosTextView.text = macroNutrients.toString()
    }

    override fun bindMealFoodDesiredServingSize(servingSize: PhysicalQuantity) {
        binding.foodQuantityEditText.setText("${servingSize.quantity}")
        binding.measurementUnitTextView.text = "${servingSize.unit}"
    }

    override fun makeDialog(): Dialog {
        return AlertDialog.Builder(getContext())
            .setView(getRootView())
            .setTitle(getString(R.string.edit_meal_food))
            .setPositiveButton(android.R.string.ok) { _, _ ->
                onPositiveButtonClicked()
            }
            .setNegativeButton(android.R.string.cancel) { _, _ -> }
            .show()
    }

    private fun onPositiveButtonClicked() {
        for (listener in getListeners()) {
            listener.onPositiveButtonClicked()
        }
    }

    override fun releaseViewRefs() {
        _binding = null
    }
}