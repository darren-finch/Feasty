package com.darrenfinch.mymealplanner.common.lists.mealsrecyclerviewadapter

import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.BaseViewHolder
import com.darrenfinch.mymealplanner.common.lists.mealfoodsrecyclerviewadapter.MealFoodsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.databinding.MealItemBinding
import com.darrenfinch.mymealplanner.model.data.entities.Meal
import net.cachapa.expandablelayout.ExpandableLayout

class MealViewHolder(private val config: MealsRecyclerViewAdapter.Config, private val listener: Listener, itemView: View) :
    BaseViewHolder<Meal>(itemView), ExpandableLayout.OnExpansionUpdateListener {
    interface Listener {
        fun onSelect(meal: Meal)
        fun onEdit(mealId: Int)
        fun onDelete(meal: Meal)
    }

    private var binding = MealItemBinding.bind(itemView)

    override fun bind(item: Meal) {
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

//    private fun displayContextMenu() {
//        PopupMenu(itemView.context, binding.dropdownImageButton, Gravity.BOTTOM).apply {
//            inflate(R.menu.context_menu)
//            setOnMenuItemClickListener { menuItem ->
//                handleMenuItemClicked(menuItem)
//            }
//            show()
//        }
//    }

//    private fun handleMenuItemClicked(menuItem: MenuItem): Boolean {
//        when (menuItem.itemId) {
//            R.id.edit -> listener.onEdit(binding.meal!!.id)
//            R.id.delete -> listener.onDelete(binding.meal!!)
//        }
//        return true
//    }

    override fun onExpansionUpdate(expansionFraction: Float, state: Int) {
        binding.dropdownImageButton.rotation = expansionFraction * 180
    }

    private fun initAdapter(meal: Meal) {
        val adapter = MealFoodsRecyclerViewAdapter()
        binding.mealFoodsRecyclerView.adapter = adapter
        binding.mealFoodsRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
        adapter.updateItems(meal.foods.toMutableList())
    }
}