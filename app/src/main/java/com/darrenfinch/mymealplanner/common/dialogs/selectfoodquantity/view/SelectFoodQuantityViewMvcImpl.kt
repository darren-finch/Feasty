package com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.view

import android.app.AlertDialog
import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.constants.DefaultModels
import com.darrenfinch.mymealplanner.common.utils.KeyboardUtils
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentSelectMealFoodQuantityBinding
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood

class SelectFoodQuantityViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : BaseObservableViewMvc<SelectFoodQuantityViewMvc.Listener>(), SelectFoodQuantityViewMvc {
    private val binding: FragmentSelectMealFoodQuantityBinding = DataBindingUtil.inflate(
        inflater,
        R.layout.fragment_select_meal_food_quantity,
        parent,
        false
    )

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
            listener.onServingSizeChange(PhysicalQuantity(binding.foodQuantityEditText.text.toString().toDouble(), binding.food!!.servingSize.unit))
        }
    }

    override fun bindFood(food: UiFood) {
        binding.food = food
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
            listener.onFoodServingSizeChosen(getFoodData(), getFoodQuantity())
        }
    }

    override fun getFoodData(): UiFood {
        return binding.food ?: DefaultModels.defaultUiFood
    }

    private fun getFoodQuantity(): PhysicalQuantity {
        val numericalQuantity = if (binding.food != null) {
            val foodQuantityText = binding.foodQuantityEditText.text.toString()
            if (foodQuantityText.isNotEmpty())
                foodQuantityText.toDouble()
            else
                0.0
        } else {
            0.0
        }
        return PhysicalQuantity(numericalQuantity, binding.food!!.servingSize.unit)
    }
}
