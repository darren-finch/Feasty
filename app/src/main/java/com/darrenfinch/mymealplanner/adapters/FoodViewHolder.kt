package com.darrenfinch.mymealplanner.adapters

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.darrenfinch.mymealplanner.model.room.Food
import com.darrenfinch.mymealplanner.databinding.FoodItemBinding

class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    private var binding = FoodItemBinding.bind(itemView)
    @SuppressLint("SetTextI18n")
    fun bind(food: Food)
    {
        binding.food = food
    }
}