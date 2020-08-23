package com.darrenfinch.mymealplanner.common.reusable.mealsrecyclerviewadapter

import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.databinding.ObservableBoolean
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.reusable.mealfoodsrecyclerviewadapter.MealFoodsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.common.utils.AnimationUtils
import com.darrenfinch.mymealplanner.databinding.MealItemBinding
import com.darrenfinch.mymealplanner.model.data.entities.Meal

class MealViewHolder(private val listener: Listener, itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    interface Listener {
        fun onEdit(mealId: Int)
        fun onDelete(meal: Meal)
    }

    private var binding = MealItemBinding.bind(itemView)
    var expanded = ObservableBoolean(false)
    fun bind(meal: Meal) {
        binding.meal = meal
        binding.viewHolder = this
        binding.viewMoreButton.setOnClickListener {
            displayContextMenu()
        }
        initAdapter(meal)
    }

    private fun displayContextMenu() {
        PopupMenu(itemView.context, binding.dropdownImageButton, Gravity.BOTTOM).apply {
            inflate(R.menu.context_menu)
            setOnMenuItemClickListener { menuItem ->
                handleMenuItemClicked(menuItem)
            }
            show()
        }
    }

    private fun handleMenuItemClicked(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.edit -> listener.onEdit(binding.meal!!.id)
            R.id.delete -> listener.onDelete(binding.meal!!)
        }
        return true
    }

    fun inverseExpanded() {
        if (expanded.get()) AnimationUtils.collapse(binding.cardBottom) else AnimationUtils.expand(
            binding.cardBottom
        )
        expanded.set(!expanded.get())
    }

    private fun initAdapter(meal: Meal) {
        val adapter =
            MealFoodsRecyclerViewAdapter(
                mutableListOf()
            )
        binding.mealFoodsRecyclerView.adapter = adapter
        binding.mealFoodsRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
        adapter.updateFoods(meal.foods.toMutableList())
    }
}