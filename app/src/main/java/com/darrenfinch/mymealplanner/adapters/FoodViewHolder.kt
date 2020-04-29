package com.darrenfinch.mymealplanner.adapters

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.darrenfinch.mymealplanner.data.FoodModel
import com.darrenfinch.mymealplanner.databinding.FoodItemBinding

class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    private var binding = FoodItemBinding.bind(itemView)
    @SuppressLint("SetTextI18n")
    fun bind(food: FoodModel)
    {
        binding.food = food
    }
}