package com.darrenfinch.mymealplanner.common.lists.mealfoodslist

import android.annotation.SuppressLint
import android.view.View
import com.darrenfinch.mymealplanner.common.lists.BaseViewHolder
import com.darrenfinch.mymealplanner.databinding.MealFoodItemBinding
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood

class MealFoodViewHolder(itemView: View) : BaseViewHolder<UiMealFood>(itemView)
{
    private var binding = MealFoodItemBinding.bind(itemView)

    @SuppressLint("SetTextI18n")
    override fun bind(item: UiMealFood) {
        binding.foodTitleTextView.text = item.title
        binding.mealFoodInfoTextView.text = "${item.desiredServingSize} | ${item.macroNutrients}"
    }
}