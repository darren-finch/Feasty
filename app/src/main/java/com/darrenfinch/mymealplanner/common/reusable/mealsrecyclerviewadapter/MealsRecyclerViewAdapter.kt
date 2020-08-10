package com.darrenfinch.mymealplanner.common.reusable.mealsrecyclerviewadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.model.data.Meal

class MealsRecyclerViewAdapter(
    private val allMeals: MutableList<Meal>,
    private val itemEventListener: ItemEventListener
) : RecyclerView.Adapter<MealViewHolder>() {
    interface ItemEventListener {
        fun onEdit(mealId: Int)
        fun onDelete(meal: Meal)
    }

    private val viewHolder = object : MealViewHolder.Listener {
        override fun onEdit(mealId: Int) {
            itemEventListener.onEdit(mealId)
        }

        override fun onDelete(meal: Meal) {
            itemEventListener.onDelete(meal)
        }
    }

    fun updateMeals(newMeals: List<Meal>) {
        allMeals.clear()
        allMeals.addAll(newMeals)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
            viewHolder,
            LayoutInflater.from(parent.context).inflate(R.layout.meal_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return allMeals.size
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int)
    {
        holder.bind(allMeals[position])
    }
}