package com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.view

import android.app.AlertDialog
import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.utils.KeyboardUtils
import com.darrenfinch.mymealplanner.common.utils.DefaultModels
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentSelectMealFoodQuantityBinding
import com.darrenfinch.mymealplanner.domain.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.model.data.entities.Food

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
            binding.food = DefaultModels.defaultFood

            binding.foodQuantityEditText.doOnTextChanged { _, _, _, _ ->
                macroNutrientsTextView.text = getFoodData().macroNutrients.toString()
            }
        }
    }

    override fun bindFood(food: Food) {
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

    private fun getFoodData(): Food {
        return binding.food?.copy(servingSize = getFoodQuantity()) ?: DefaultModels.defaultFood
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
