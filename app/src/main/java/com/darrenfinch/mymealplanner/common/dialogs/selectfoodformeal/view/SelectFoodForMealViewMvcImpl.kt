package com.darrenfinch.mymealplanner.common.dialogs.selectfoodformeal.view

import android.app.AlertDialog
import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.foodrecyclerviewadapter.FoodsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.common.lists.recyclerviewitemdecorations.MarginItemDecoration
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentSelectItemBinding
import com.darrenfinch.mymealplanner.foods.models.Food

class SelectFoodForMealViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : BaseObservableViewMvc<SelectFoodForMealViewMvc.Listener>(), SelectFoodForMealViewMvc {

    private val binding: FragmentSelectItemBinding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_select_item, parent, false)

    private val foodsListItemEventListener = object : FoodsRecyclerViewAdapter.ItemEventListener {
        override fun onItemClick(foodId: Int) {}
        override fun onItemClick(food: Food) {
            for (listener in getListeners()) {
                listener.onFoodChosen(food)
            }
        }
        override fun onItemEdit(foodId: Int) {}
        override fun onItemDelete(foodId: Int) {}
    }

    private val adapter = FoodsRecyclerViewAdapter(FoodsRecyclerViewAdapter.Config(showViewMoreButton = false), foodsListItemEventListener)

    init {
        setRootView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.apply {
            itemsRecyclerView.adapter = adapter
            itemsRecyclerView.layoutManager = LinearLayoutManager(getContext())
            itemsRecyclerView.addItemDecoration(
                MarginItemDecoration(
                    16
                )
            )
        }
    }

    override fun bindFoods(foods: List<Food>) {
        adapter.updateItems(foods)
    }

    override fun makeDialog(): Dialog {
        return AlertDialog.Builder(getContext())
            .setView(getRootView())
            .setTitle(getString(R.string.select_food))
            .setNegativeButton(android.R.string.cancel) { _, _ -> }
            .show()
    }
}
