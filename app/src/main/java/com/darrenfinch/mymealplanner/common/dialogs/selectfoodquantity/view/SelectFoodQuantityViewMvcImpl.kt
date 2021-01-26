package com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.view

import android.app.AlertDialog
import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.utils.KeyboardUtils
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentSelectMealFoodQuantityBinding
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.foods.models.presentation.UiMacroNutrients
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity

class SelectFoodQuantityViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : BaseObservableViewMvc<SelectFoodQuantityViewMvc.Listener>(), SelectFoodQuantityViewMvc {
    private var _binding: FragmentSelectMealFoodQuantityBinding? = DataBindingUtil.inflate(
        inflater,
        R.layout.fragment_select_meal_food_quantity,
        parent,
        false
    )
    private val binding = _binding!!

    init {
        setRootView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.apply {
            binding.foodQuantityEditText.doOnTextChanged { _, _, _, _ ->
                onServingSizeChange()
            }
        }
    }

    private fun onServingSizeChange() {
        for (listener in getListeners()) {
            listener.onServingSizeQuantityChange(binding.foodQuantityEditText.text.toString().toDoubleOrNull() ?: 0.0)
        }
    }

    override fun bindProperties(title: String, macroNutrients: UiMacroNutrients, selectedQuantity: PhysicalQuantity) {
        bindServingSize(selectedQuantity)
        bindFoodTitle(title)
        bindMacros(macroNutrients)
    }

    override fun bindServingSize(servingSize: PhysicalQuantity) {
        binding.foodQuantityEditText.setText(servingSize.quantity.toString())
        binding.measurementUnitTextView.text = servingSize.unit.toString()
    }

    override fun bindFoodTitle(title: String) {
        binding.foodNameTextView.text = title
    }

    override fun bindMacros(macroNutrients: UiMacroNutrients) {
        binding.mealFoodInfoTextView.text = macroNutrients.toString()
    }

    override fun makeDialog(): Dialog {
        return AlertDialog.Builder(getContext())
            .setView(getRootView())
            .setTitle(getString(R.string.select_food_quantity))
            .setPositiveButton(android.R.string.ok) { _, _ ->
                onPositiveButtonClicked()
            }
            .setNegativeButton(android.R.string.cancel) { _, _ -> }
            .show()
    }

    private fun onPositiveButtonClicked() {
        KeyboardUtils.hideKeyboardFrom(getContext(), getRootView())

        for (listener in getListeners()) {
            listener.onPositiveButtonClicked()
        }
    }

    override fun releaseViewRefs() {
        _binding = null
    }
}
