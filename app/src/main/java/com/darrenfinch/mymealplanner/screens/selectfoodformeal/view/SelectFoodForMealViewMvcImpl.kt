package com.darrenfinch.mymealplanner.screens.selectfoodformeal.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.foodslist.FoodsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.common.lists.itemdecorations.MarginItemDecoration
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentSelectItemBinding
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood

class SelectFoodForMealViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : BaseObservableViewMvc<SelectFoodForMealViewMvc.Listener>(), SelectFoodForMealViewMvc {

    private var _binding: FragmentSelectItemBinding? =
        DataBindingUtil.inflate(inflater, R.layout.fragment_select_item, parent, false)
    private val binding = _binding!!

    private val foodsListItemEventListener = object : FoodsRecyclerViewAdapter.ItemEventListener {
        override fun onItemClick(foodId: Int) {}
        override fun onItemClick(food: UiFood) {
            for (listener in getListeners()) {
                listener.onFoodChosen(food)
            }
        }

        override fun onItemEdit(foodId: Int) {}
        override fun onItemDelete(foodId: Int) {}
    }

    private val adapter = FoodsRecyclerViewAdapter(
        FoodsRecyclerViewAdapter.Config(showViewMoreButton = false),
        foodsListItemEventListener
    )

    init {
        setRootView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                for (listener in getListeners()) {
                    listener.onNavigateUp()
                }
            }
            toolbar.setTitle(R.string.select_food)

            itemsRecyclerView.adapter = adapter
            itemsRecyclerView.layoutManager = LinearLayoutManager(getContext())
            itemsRecyclerView.addItemDecoration(
                MarginItemDecoration(
                    16
                )
            )

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        for (listener in getListeners()) {
                            listener.onQuerySubmitted(query)
                        }
                        return true
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    // no-op
                    return false
                }
            })
        }
    }

    override fun bindFoods(foods: List<UiFood>) {
        adapter.updateItems(foods)
    }

    override fun releaseViewRefs() {
        _binding = null
    }

    override fun showProgressIndication() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressIndication() {
        binding.progressBar.visibility = View.GONE
    }
}
