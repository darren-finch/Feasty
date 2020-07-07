package com.darrenfinch.mymealplanner.domain.selectfoodformeal.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.reusable.foodrecyclerviewadapter.FoodsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.common.reusable.recyclerviewitemdecorations.MarginItemDecoration
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentSelectFoodForMealBinding
import com.darrenfinch.mymealplanner.model.data.Food

class SelectFoodForMealViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : BaseObservableViewMvc<SelectFoodForMealViewMvc.Listener>(), SelectFoodForMealViewMvc {

    private val binding: FragmentSelectFoodForMealBinding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_select_food_for_meal, parent, false)

    private val foodsListItemEventListener = object : FoodsRecyclerViewAdapter.ItemEventListener {
        override fun onItemClick(foodId: Int) {
            for (listener in getListeners()) {
                listener.onFoodChosen(foodId)
            }
        }

        override fun onItemEdit(foodId: Int) {}
        override fun onItemDelete(foodId: Int) {}
    }

    private val foodsListAdapter =
        FoodsRecyclerViewAdapter(FoodsRecyclerViewAdapter.Config(false), mutableListOf()).apply {
            setOnItemEventListener(foodsListItemEventListener)
        }

    init {
        setRootView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.apply {
            foodsRecyclerView.adapter = foodsListAdapter
            foodsRecyclerView.layoutManager = LinearLayoutManager(getContext())
            foodsRecyclerView.addItemDecoration(
                MarginItemDecoration(
                    16
                )
            )
        }
    }

    override fun bindFoods(foodsFromDatabase: List<Food>) {
        foodsListAdapter.updateFoods(foodsFromDatabase)
    }
}
