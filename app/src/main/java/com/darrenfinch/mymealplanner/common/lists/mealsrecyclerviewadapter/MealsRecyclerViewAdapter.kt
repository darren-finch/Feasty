package com.darrenfinch.mymealplanner.common.lists.mealsrecyclerviewadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.BaseRecyclerViewAdapter
import com.darrenfinch.mymealplanner.model.data.entities.Meal

class MealsRecyclerViewAdapter(
    private val config: Config,
    private val itemEventListener: ItemEventListener
) : BaseRecyclerViewAdapter<Meal, MealViewHolder>(mutableListOf()), MealViewHolder.Listener {
    data class Config(val showViewMoreButton: Boolean = true)

    interface ItemEventListener {
        fun onSelect(meal: Meal)
        fun onEdit(mealId: Int)
        fun onDelete(meal: Meal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
            config,
            this,
            LayoutInflater.from(parent.context).inflate(R.layout.meal_item, parent, false)
        )
    }

    override fun onSelect(meal: Meal) {
        itemEventListener.onSelect(meal)
    }

    override fun onEdit(mealId: Int) {
        itemEventListener.onEdit(mealId)
    }

    override fun onDelete(meal: Meal) {
        itemEventListener.onDelete(meal)
    }
}