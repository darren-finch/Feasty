package com.darrenfinch.mymealplanner.common.lists.mealslist

import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.BaseViewHolder
import com.darrenfinch.mymealplanner.common.lists.mealfoodslist.MealFoodsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.databinding.MealItemBinding
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import net.cachapa.expandablelayout.ExpandableLayout

class MealViewHolder(private val config: MealsRecyclerViewAdapter.Config, private val listener: Listener, itemView: View) :
    BaseViewHolder<UiMeal>(itemView), ExpandableLayout.OnExpansionUpdateListener {
    interface Listener {
        fun onSelect(meal: UiMeal)
        fun onEdit(mealId: Int)
        fun onDelete(meal: UiMeal)
    }

    private var binding = MealItemBinding.bind(itemView)

    override fun bind(item: UiMeal) {
        binding.apply {
            meal = item
            viewHolder = this@MealViewHolder
            cardBottom.setOnExpansionUpdateListener(this@MealViewHolder)

            cardTop.setOnClickListener {
                listener.onSelect(item)
            }

            dropdownImageButton.setOnClickListener {
                cardBottom.toggle()
            }

            if (config.showViewMoreButton) {
                viewMoreButton.setOnClickListener {
                    PopupMenu(itemView.context, dropdownImageButton, Gravity.BOTTOM).apply {
                        inflate(R.menu.context_menu)
                        setOnMenuItemClickListener { menuItem ->
                            when (menuItem.itemId) {
                                R.id.edit -> listener.onEdit(item.id)
                                R.id.delete -> listener.onDelete(item)
                            }
                            true
                        }
                        show()
                    }
                }
            } else {
                viewMoreButton.visibility = View.GONE
            }

            initAdapter(item)
        }

    }

    override fun onExpansionUpdate(expansionFraction: Float, state: Int) {
        binding.dropdownImageButton.rotation = expansionFraction * 180
    }

    private fun initAdapter(meal: UiMeal) {
        val adapter = MealFoodsRecyclerViewAdapter()
        binding.mealFoodsRecyclerView.adapter = adapter
        binding.mealFoodsRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
        adapter.updateItems(meal.foods.toMutableList())
    }
}