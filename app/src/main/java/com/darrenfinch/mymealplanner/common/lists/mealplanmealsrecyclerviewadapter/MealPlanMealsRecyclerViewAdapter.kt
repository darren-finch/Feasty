package com.darrenfinch.mymealplanner.common.lists.mealplanmealsrecyclerviewadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.BaseRecyclerViewAdapter
import com.darrenfinch.mymealplanner.mealplans.models.MealPlanMeal

class MealPlanMealsRecyclerViewAdapter(private val listener: ItemEventListener) :
    BaseRecyclerViewAdapter<MealPlanMeal, MealPlanMealViewHolder>(mutableListOf()),
    MealPlanMealViewHolder.Listener {
    interface ItemEventListener {
        fun onDelete(mealPlanMeal: MealPlanMeal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealPlanMealViewHolder {
        return MealPlanMealViewHolder(this, LayoutInflater.from(parent.context).inflate(R.layout.meal_plan_meal_item, parent, false))
    }

    override fun onDelete(mealPlanMeal: MealPlanMeal) {
        listener.onDelete(mealPlanMeal)
    }
}