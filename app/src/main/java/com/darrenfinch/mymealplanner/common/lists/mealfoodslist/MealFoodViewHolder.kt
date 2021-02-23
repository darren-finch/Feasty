package com.darrenfinch.mymealplanner.common.lists.mealfoodslist

import android.annotation.SuppressLint
import android.view.View
import android.widget.PopupMenu
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.BaseViewHolder
import com.darrenfinch.mymealplanner.databinding.MealFoodItemBinding
import com.darrenfinch.mymealplanner.foods.services.MacroCalculatorService
import com.darrenfinch.mymealplanner.meals.models.mappers.uiMealFoodToMealFood
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood

class MealFoodViewHolder(
    private val config: MealFoodsRecyclerViewAdapter.Config,
    private val listener: Listener,
    itemView: View
) : BaseViewHolder<UiMealFood>(itemView) {
    interface Listener {
        fun onEdit(mealFood: UiMealFood, index: Int)
        fun onDelete(index: Int)
    }

    private val binding = MealFoodItemBinding.bind(itemView)

    @SuppressLint("SetTextI18n")
    override fun bind(item: UiMealFood) {
        binding.apply {
            val macrosBasedOnDesiredServingSize = MacroCalculatorService.baseMacrosOnDesiredServingSize(item.originalMacros, item.originalServingSize, item.desiredServingSize)

            if(config.showAsFullCard) {
                mealFoodCardLayout.visibility = View.VISIBLE

                foodTitleTextViewCard.text = item.title
                mealFoodMacrosTextViewCard.text = "${item.desiredServingSize.getAsString(true)} | $macrosBasedOnDesiredServingSize"

                viewMoreButtonCard.setOnClickListener {
                    PopupMenu(itemView.context, viewMoreButtonCard).apply {
                        inflate(R.menu.context_menu)

                        setOnMenuItemClickListener { menuItem ->
                            when (menuItem.itemId) {
                                R.id.edit -> listener.onEdit(item, adapterPosition)
                                R.id.delete -> listener.onDelete(adapterPosition)
                            }
                            true
                        }
                        show()
                    }
                }
            }
            else {
                mealFoodRegularLayout.visibility = View.VISIBLE

                foodTitleTextViewRegular.text = item.title
                mealFoodMacrosTextViewRegular.text = "${item.desiredServingSize.getAsString(true)} | $macrosBasedOnDesiredServingSize"

                viewMoreButtonRegular.visibility = View.GONE
            }
        }
    }
}