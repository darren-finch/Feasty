package com.darrenfinch.mymealplanner.reusable.mealsrecyclerviewadapter

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.darrenfinch.mymealplanner.model.data.Meal
import com.darrenfinch.mymealplanner.databinding.MealItemBinding
import com.darrenfinch.mymealplanner.model.data.DataConverters
import com.darrenfinch.mymealplanner.reusable.mealfoodsrecyclerviewadapter.MealFoodsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.utils.AnimationUtils

class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    private var binding = MealItemBinding.bind(itemView)
    var expanded = ObservableBoolean(false)
    fun bind(meal: Meal)
    {
        binding.meal = meal
        binding.viewHolder = this
        initAdapter(meal)
    }
    fun inverseExpanded()
    {
        if(expanded.get()) AnimationUtils.collapse(binding.foodsRecyclerView) else AnimationUtils.expand(binding.foodsRecyclerView)
        expanded.set(!expanded.get())
    }
    private fun initAdapter(meal: Meal)
    {
        val adapter =
            MealFoodsRecyclerViewAdapter(
                meal.foods.map { DataConverters.convertMealFoodToFood(it) })
        binding.foodsRecyclerView.adapter = adapter
        binding.foodsRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
        adapter.notifyDataSetChanged()
    }
}