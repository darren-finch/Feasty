package com.darrenfinch.mymealplanner.common.reusable.mealfoodsrecyclerviewadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.model.data.MealFood

class MealFoodsRecyclerViewAdapter(private val allMealFoods: MutableList<MealFood>) :
    RecyclerView.Adapter<MealFoodViewHolder>() {
    fun addNewMealFood(newMealFood: MealFood) {
        allMealFoods.add(newMealFood)
        notifyDataSetChanged()
    }

    fun updateFoods(newFoods: List<MealFood>) {
        allMealFoods.clear()
        allMealFoods.addAll(newFoods)
        notifyDataSetChanged()
    }

    fun getAllMealFoods(): List<MealFood> {
        return allMealFoods
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealFoodViewHolder {
        return MealFoodViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.meal_food_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return allMealFoods.size
    }

    override fun onBindViewHolder(holder: MealFoodViewHolder, position: Int) {
        holder.bind(allMealFoods[position])
    }
}