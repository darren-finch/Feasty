package com.darrenfinch.mymealplanner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.data.FoodModel

class FoodsRecyclerViewAdapter(private val allFoods: MutableList<FoodModel>) : RecyclerView.Adapter<FoodViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder
    {
        return FoodViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.food_item, parent, false))
    }
    override fun getItemCount(): Int
    {
        return allFoods.size
    }
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int)
    {
        holder.bind(allFoods[position])
    }
}