package com.darrenfinch.mymealplanner.common.lists.mealfoodslist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.BaseRecyclerViewAdapter
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood

class MealFoodsRecyclerViewAdapter(
    private val config: Config = Config(),
    private val listener: ItemEventListener? = null
) :
    BaseRecyclerViewAdapter<UiMealFood, MealFoodViewHolder>(mutableListOf()),
    MealFoodViewHolder.Listener {

    data class Config(val showAsFullCard: Boolean = false)

    interface ItemEventListener {
        fun onItemEdit(mealFood: UiMealFood, index: Int)
        fun onItemDelete(index: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealFoodViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.meal_food_item, parent, false)
        return MealFoodViewHolder(
            config,
            this,
            itemView
        )
    }

    override fun onEdit(mealFood: UiMealFood, index: Int) {
        listener?.onItemEdit(mealFood, index)
    }

    override fun onDelete(index: Int) {
        listener?.onItemDelete(index)
    }
}