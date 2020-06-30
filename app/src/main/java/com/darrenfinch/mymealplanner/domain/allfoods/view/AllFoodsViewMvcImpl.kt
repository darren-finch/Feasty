package com.darrenfinch.mymealplanner.domain.allfoods.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.reusable.foodrecyclerviewadapter.FoodsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.common.reusable.recyclerviewitemdecorations.MarginItemDecoration
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentAllFoodsBinding
import com.darrenfinch.mymealplanner.model.data.Food

class AllFoodsViewMvcImpl(inflater: LayoutInflater, parent: ViewGroup?) : BaseObservableViewMvc<AllFoodsViewMvc.Listener>(), AllFoodsViewMvc {

    private val foodsListItemEventListener: FoodsRecyclerViewAdapter.ItemEventListener = object : FoodsRecyclerViewAdapter.ItemEventListener {
        override fun onItemClick(foodId: Int) {}

        override fun onItemEdit(foodId: Int) {
            notifyListenersOfItemEdit(foodId)
        }
        override fun onItemDelete(foodId: Int) {
            notifyListenersOfItemDelete(foodId)
        }
    }

    private fun notifyListenersOfItemDelete(foodId: Int) {
        for(listener in getListeners())
            listener.onItemDelete(foodId)
    }

    private fun notifyListenersOfItemEdit(foodId: Int) {
        for(listener in getListeners()) {
            listener.onItemEdit(foodId)
        }
    }

    private val foodsListAdapter = FoodsRecyclerViewAdapter(FoodsRecyclerViewAdapter.Config(true), mutableListOf()).apply {
        setOnItemEventListener(foodsListItemEventListener)
    }

    private val binding: FragmentAllFoodsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_foods, parent, false)

    init {
        setRootView(binding.root)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            foodsRecyclerView.adapter = foodsListAdapter
            foodsRecyclerView.layoutManager = LinearLayoutManager(getContext())
            foodsRecyclerView.addItemDecoration(MarginItemDecoration(16))

            addNewFood.setOnClickListener {
                notifyListenersOfAddNewFoodClicked()
            }
        }
    }

    private fun notifyListenersOfAddNewFoodClicked() {
        for(listener in getListeners()) {
            listener.addNewFoodClicked()
        }
    }

    override fun bindFoods(newFoods: List<Food>) {
        foodsListAdapter.updateFoods(newFoods)
    }
}