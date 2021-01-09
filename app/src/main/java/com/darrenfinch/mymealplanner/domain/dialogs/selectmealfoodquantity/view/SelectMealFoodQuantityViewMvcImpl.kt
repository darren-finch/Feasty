package com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.view

import android.app.AlertDialog
import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.misc.KeyboardUtils
import com.darrenfinch.mymealplanner.common.utils.Defaults
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentSelectMealFoodQuantityBinding
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.view.SelectMealFoodQuantityViewMvc
import com.darrenfinch.mymealplanner.domain.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.model.data.entities.Food
import com.darrenfinch.mymealplanner.model.data.entities.MealFood
import com.darrenfinch.mymealplanner.model.helpers.MacroCalculator

class SelectMealFoodQuantityViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?,
    private val mealId: Int
) : BaseObservableViewMvc<SelectMealFoodQuantityViewMvc.Listener>(), SelectMealFoodQuantityViewMvc {
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
            binding.food = Defaults.defaultFood

            binding.foodQuantityEditText.doOnTextChanged { _, _, _, _ ->
                macroNutrientsTextView.text = getUpdatedMealFoodData().macroNutrients.toString()
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
            listener.onMealFoodServingSizeChosen(getUpdatedMealFoodData())
        }
    }

    private fun getUpdatedMealFoodData(): MealFood {
        binding.food?.let {
            return MacroCalculator.updateMacrosForMealFoodWithNewServingSize(
                MealFood(
                    0,
                    it.id,
                    mealId,
                    it.title,
                    it.servingSize,
                    it.macroNutrients
                ),
                PhysicalQuantity(
                    getFoodQuantity(),
                    it.servingSize.unit
                )
            )
        }

        return Defaults.defaultMealFood
    }

    private fun getFoodQuantity(): Double {
        return if (binding.food != null) {
            val foodQuantityText = binding.foodQuantityEditText.text.toString()
            if (foodQuantityText.isNotEmpty())
                foodQuantityText.toDouble()
            else
                0.0
        } else {
            0.0
        }
    }
}
