package com.darrenfinch.mymealplanner.common.reusable.foodrecyclerviewadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.model.data.Food

class FoodsRecyclerViewAdapter(private val config: Config, private val allFoods: MutableList<Food>) :
    RecyclerView.Adapter<FoodViewHolder>() {

    interface ItemEventListener {
        fun onItemClick(foodId: Int)
        fun onItemEdit(foodId: Int)
        fun onItemDelete(foodId: Int)
    }

    data class Config(
        val showViewMoreButton: Boolean = true
    )

    private var itemEventListener: ItemEventListener? = null
    private val viewHolderEventListener = object :
        FoodViewHolder.EventListener {
        override fun onClick(foodId: Int) {
            itemEventListener?.onItemClick(foodId)
        }

        override fun onEdit(foodId: Int) {
            itemEventListener?.onItemEdit(foodId)
        }

        override fun onDelete(foodId: Int) {
            itemEventListener?.onItemDelete(foodId)
        }
    }

    fun setOnItemEventListener(itemEventListener: ItemEventListener) {
        this.itemEventListener = itemEventListener
    }

    fun updateFoods(newFoods: List<Food>) {
        allFoods.clear()
        allFoods.addAll(newFoods)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return FoodViewHolder(
            config,
            viewHolderEventListener,
            LayoutInflater.from(parent.context).inflate(R.layout.food_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return allFoods.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(allFoods[position])
    }
}