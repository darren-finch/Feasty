package com.darrenfinch.mymealplanner.adapters

import android.annotation.SuppressLint
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.databinding.FoodItemBinding
import com.darrenfinch.mymealplanner.model.data.Food

class FoodViewHolder(private val config: FoodsRecyclerViewAdapter.Config, private val eventListener: EventListener, itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    interface EventListener {
        fun onClick(foodId: Int)
        fun onEdit(foodId: Int)
        fun onDelete(foodId: Int)
    }

    private var binding = FoodItemBinding.bind(itemView)

    @SuppressLint("SetTextI18n")
    fun bind(food: Food) {
        binding.food = food
        setupUI()
    }
    private fun setupUI() {
        binding?.food?.let { food ->
            setupViewMoreButton()
            binding.foodCardView.setOnClickListener {
                eventListener.onClick(food.id)
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
            inflate(R.menu.food_view_more_menu)
            setOnMenuItemClickListener { menuItem ->
                onMenuItemClicked(menuItem.itemId)
                true
            }
            show()
        }
    }
    private fun onMenuItemClicked(itemId: Int) {
        when (itemId) {
            R.id.editFood -> {
                binding?.food?.id?.let { foodId ->
                    eventListener.onEdit(foodId)
                }
            }
            R.id.deleteFood -> {
                binding?.food?.id?.let { foodId ->
                    eventListener.onDelete(foodId)
                }
            }
        }
    }
}