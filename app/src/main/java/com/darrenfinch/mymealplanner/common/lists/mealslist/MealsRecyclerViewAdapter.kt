package com.darrenfinch.mymealplanner.common.lists.mealslist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.BaseRecyclerViewAdapter
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal

class MealsRecyclerViewAdapter(
    private val config: Config,
    private val itemEventListener: ItemEventListener
) : BaseRecyclerViewAdapter<UiMeal, MealViewHolder>(mutableListOf()), MealViewHolder.Listener {
    data class Config(val showViewMoreButton: Boolean = true, val allowEditingItems: Boolean = true)

    interface ItemEventListener {
        fun onSelect(meal: UiMeal)
        fun onEdit(mealId: Int)
        fun onDelete(mealId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
            config,
            this,
            LayoutInflater.from(parent.context).inflate(R.layout.meal_item, parent, false)
        )
    }

    override fun onSelect(meal: UiMeal) {
        itemEventListener.onSelect(meal)
    }

    override fun onEdit(mealId: Int) {
        itemEventListener.onEdit(mealId)
    }

    override fun onDelete(mealId: Int) {
        itemEventListener.onDelete(mealId)
    }
}