package com.darrenfinch.mymealplanner.common.lists.mealfoodslist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.BaseRecyclerViewAdapter
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood

class MealFoodsRecyclerViewAdapter :
    BaseRecyclerViewAdapter<UiMealFood, MealFoodViewHolder>(mutableListOf()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealFoodViewHolder {
        return MealFoodViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.meal_food_item, parent, false)
        )
    }
}