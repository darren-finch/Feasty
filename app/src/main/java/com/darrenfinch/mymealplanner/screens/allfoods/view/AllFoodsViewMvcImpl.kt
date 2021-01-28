package com.darrenfinch.mymealplanner.screens.allfoods.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.foodslist.FoodsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.common.lists.itemdecorations.MarginItemDecoration
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentAllFoodsBinding
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood

class AllFoodsViewMvcImpl(inflater: LayoutInflater, parent: ViewGroup?) : BaseObservableViewMvc<AllFoodsViewMvc.Listener>(), AllFoodsViewMvc {

    private val foodsListItemEventListener: FoodsRecyclerViewAdapter.ItemEventListener = object : FoodsRecyclerViewAdapter.ItemEventListener {
        override fun onItemClick(foodId: Int) { }
        override fun onItemClick(food: UiFood) { }

        override fun onItemEdit(foodId: Int) {
            notifyListenersOfItemEdit(foodId)
        }
        override fun onItemDelete(foodId: Int) {
            notifyListenersOfItemDelete(foodId)
        }
    }

    private fun notifyListenersOfItemDelete(foodId: Int) {
        for(listener in getListeners())
            listener.onFoodDelete(foodId)
    }

    private fun notifyListenersOfItemEdit(foodId: Int) {
        for(listener in getListeners()) {
            listener.onFoodEdit(foodId)
        }
    }

    private val foodsListAdapter = FoodsRecyclerViewAdapter(FoodsRecyclerViewAdapter.Config(true), foodsListItemEventListener)

    private var _binding: FragmentAllFoodsBinding? = DataBindingUtil.inflate(inflater, R.layout.fragment_all_foods, parent, false)
    private val binding = _binding!!

    init {
        setRootView(binding.root)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            foodsRecyclerView.adapter = foodsListAdapter
            foodsRecyclerView.layoutManager = LinearLayoutManager(getContext())
            foodsRecyclerView.addItemDecoration(MarginItemDecoration(16))
            addFoodFab.setOnClickListener { onAddNewFoodClicked() }
        }
    }

    private fun onAddNewFoodClicked() {
        for(listener in getListeners()) {
            listener.onAddNewFoodClicked()
        }
    }

    override fun bindFoods(newFoods: List<UiFood>) {
        foodsListAdapter.updateItems(newFoods)
    }

    override fun releaseViewRefs() {
        _binding = null
    }

    override fun showProgressIndication() {
        binding.foodsRecyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressIndication() {
        binding.foodsRecyclerView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }
}