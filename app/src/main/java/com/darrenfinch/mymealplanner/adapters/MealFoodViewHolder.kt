package com.darrenfinch.mymealplanner.adapters

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.darrenfinch.mymealplanner.data.FoodModel
import com.darrenfinch.mymealplanner.databinding.MealFoodItemBinding

class MealFoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    private var binding = MealFoodItemBinding.bind(itemView)

    @SuppressLint("SetTextI18n")
    fun bind(food: FoodModel)
    {
        binding.food = food
    }
}