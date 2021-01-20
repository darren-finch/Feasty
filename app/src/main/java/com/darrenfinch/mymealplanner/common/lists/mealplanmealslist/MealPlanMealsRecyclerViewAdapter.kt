package com.darrenfinch.mymealplanner.common.lists.mealplanmealslist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.BaseRecyclerViewAdapter
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlanMeal

class MealPlanMealsRecyclerViewAdapter(private val listener: ItemEventListener) :
    BaseRecyclerViewAdapter<UiMealPlanMeal, MealPlanMealViewHolder>(mutableListOf()),
    MealPlanMealViewHolder.Listener {
    interface ItemEventListener {
        fun onDelete(mealPlanMeal: UiMealPlanMeal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealPlanMealViewHolder {
        return MealPlanMealViewHolder(this, LayoutInflater.from(parent.context).inflate(R.layout.meal_plan_meal_item, parent, false))
    }

    override fun onDelete(mealPlanMeal: UiMealPlanMeal) {
        listener.onDelete(mealPlanMeal)
    }
}