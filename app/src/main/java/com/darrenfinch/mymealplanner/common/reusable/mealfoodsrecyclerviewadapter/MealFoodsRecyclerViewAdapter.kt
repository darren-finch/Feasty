package com.darrenfinch.mymealplanner.common.reusable.mealfoodsrecyclerviewadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.model.data.Food

class MealFoodsRecyclerViewAdapter(private val allFoods: List<Food>) : RecyclerView.Adapter<MealFoodViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealFoodViewHolder
    {
        return MealFoodViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.meal_food_item, parent, false)
        )
    }
    override fun getItemCount(): Int
    {
        return allFoods.size
    }
    override fun onBindViewHolder(holder: MealFoodViewHolder, position: Int)
    {
        holder.bind(allFoods[position])
    }
}