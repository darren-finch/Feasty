package com.darrenfinch.mymealplanner.common.lists.foodslist

import android.annotation.SuppressLint
import android.view.View
import android.widget.PopupMenu
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.BaseViewHolder
import com.darrenfinch.mymealplanner.databinding.FoodItemBinding
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood

class FoodViewHolder(private val config: FoodsRecyclerViewAdapter.Config, private val eventListener: EventListener, itemView: View) :
    BaseViewHolder<UiFood>(itemView) {

    interface EventListener {
        fun onClick(foodId: Int)
        fun onClick(food: UiFood)
        fun onEdit(foodId: Int)
        fun onDelete(foodId: Int)
    }

    private var binding = FoodItemBinding.bind(itemView)

    @SuppressLint("SetTextI18n")
    override fun bind(item: UiFood) {
        binding.food = item
        setupUI()
    }
    private fun setupUI() {
        binding.food?.let { food ->
            setupViewMoreButton()
            binding.foodCardView.setOnClickListener {
                eventListener.onClick(food.id)
                eventListener.onClick(food)
            }
        }
    }
    private fun setupViewMoreButton() {
        if(!config.showViewMoreButton) binding.viewMoreButton.visibility = View.GONE
        binding.viewMoreButton.setOnClickListener {
            openViewMoreMenu()
        }
    }
    private fun openViewMoreMenu() {
        PopupMenu(itemView.context, binding.viewMoreButton).apply {
            inflate(R.menu.context_menu)
            setOnMenuItemClickListener { menuItem ->
                onMenuItemClicked(menuItem.itemId)
                true
            }
            show()
        }
    }
    private fun onMenuItemClicked(itemId: Int) {
        when (itemId) {
            R.id.edit -> {
                binding.food?.id?.let { foodId ->
                    eventListener.onEdit(foodId)
                }
            }
            R.id.delete -> {
                binding.food?.id?.let { foodId ->
                    eventListener.onDelete(foodId)
                }
            }
        }
    }
}