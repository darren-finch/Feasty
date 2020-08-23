package com.darrenfinch.mymealplanner.domain.selectmealfoodquantity.view

import android.app.AlertDialog
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.utils.Defaults
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentSelectMealFoodQuantityBinding
import com.darrenfinch.mymealplanner.model.data.entities.Food
import com.darrenfinch.mymealplanner.model.data.entities.MealFood

class SelectMealFoodQuantityViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : BaseObservableViewMvc<SelectMealFoodViewMvc.Listener>(), SelectMealFoodViewMvc {

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
            foodUnitSpinner.visibility = View.GONE //because we won't need this spinner for now.
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
        for (listener in getListeners()) {
            listener.onMealFoodQuantityChosen(getMealFoodData())
        }
    }

    private fun getMealFoodData(): MealFood {
        binding.food?.let {
            return MealFood(
                it.id,
                it.title,
                binding.foodQuantityEditText.text.toString().toDouble(),
                it.servingSize,
                it.servingSizeUnit,
                it.macroNutrients
            )
        }

        return Defaults.defaultMealFood
    }
}
