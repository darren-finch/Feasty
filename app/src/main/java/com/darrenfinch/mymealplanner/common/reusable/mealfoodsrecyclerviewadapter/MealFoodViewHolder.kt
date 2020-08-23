package com.darrenfinch.mymealplanner.common.reusable.mealfoodsrecyclerviewadapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.darrenfinch.mymealplanner.databinding.MealFoodItemBinding
import com.darrenfinch.mymealplanner.model.data.entities.MealFood

class MealFoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    private var binding = MealFoodItemBinding.bind(itemView)

    @SuppressLint("SetTextI18n")
    fun bind(food: MealFood) {
        binding.mealFood = food
    }
}