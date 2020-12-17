package com.darrenfinch.mymealplanner.common.lists.mealsrecyclerviewadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.BaseRecyclerViewAdapter
import com.darrenfinch.mymealplanner.model.data.entities.Meal

class MealsRecyclerViewAdapter(
    private val itemEventListener: ItemEventListener
) : BaseRecyclerViewAdapter<Meal, MealViewHolder>(mutableListOf()), MealViewHolder.Listener {
    interface ItemEventListener {
        fun onEdit(mealId: Int)
        fun onDelete(meal: Meal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
            this,
            LayoutInflater.from(parent.context).inflate(R.layout.meal_item, parent, false)
        )
    }

    override fun onEdit(mealId: Int) {
        itemEventListener.onEdit(mealId)
    }

    override fun onDelete(meal: Meal) {
        itemEventListener.onDelete(meal)
    }
}