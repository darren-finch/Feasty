package com.darrenfinch.mymealplanner.common.lists.foodrecyclerviewadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.BaseRecyclerViewAdapter
import com.darrenfinch.mymealplanner.model.data.entities.Food

class FoodsRecyclerViewAdapter(private val config: Config, private val itemEventListener: ItemEventListener) :
    BaseRecyclerViewAdapter<Food, FoodViewHolder>(mutableListOf()), FoodViewHolder.EventListener {

    interface ItemEventListener {
        fun onItemClick(foodId: Int)
        fun onItemClick(food: Food)
        fun onItemEdit(foodId: Int)
        fun onItemDelete(foodId: Int)
    }

    data class Config(
        val showViewMoreButton: Boolean = true
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return FoodViewHolder(
            config,
            this,
            LayoutInflater.from(parent.context).inflate(R.layout.food_item, parent, false)
        )
    }

    override fun onClick(foodId: Int) {
        itemEventListener.onItemClick(foodId)
    }

    override fun onClick(food: Food) {
        itemEventListener.onItemClick(food)
    }

    override fun onEdit(foodId: Int) {
        itemEventListener.onItemEdit(foodId)
    }

    override fun onDelete(foodId: Int) {
        itemEventListener.onItemDelete(foodId)
    }
}