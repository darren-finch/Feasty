package com.darrenfinch.mymealplanner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.model.data.Meal

class MealsRecyclerViewAdapter(private val allMeals: MutableList<Meal>) : RecyclerView.Adapter<MealViewHolder>()
{
    fun updateMeals(newMeals: List<Meal>)
    {
        allMeals.clear()
        allMeals.addAll(newMeals)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder
    {
        return MealViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.meal_item, parent, false))
    }
    override fun getItemCount(): Int
    {
        return allMeals.size
    }
    override fun onBindViewHolder(holder: MealViewHolder, position: Int)
    {
        holder.bind(allMeals[position])
    }
}