package com.darrenfinch.mymealplanner.common.lists.mealfoodsrecyclerviewadapter

import android.annotation.SuppressLint
import android.view.View
import com.darrenfinch.mymealplanner.common.lists.BaseViewHolder
import com.darrenfinch.mymealplanner.databinding.MealFoodItemBinding
import com.darrenfinch.mymealplanner.meals.models.MealFood

class MealFoodViewHolder(itemView: View) : BaseViewHolder<MealFood>(itemView)
{
    private var binding = MealFoodItemBinding.bind(itemView)

    @SuppressLint("SetTextI18n")
    override fun bind(item: MealFood) {
        binding.mealFood = item
    }
}