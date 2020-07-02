package com.darrenfinch.mymealplanner.domain.selectfoodformeal.view

import android.app.AlertDialog
import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
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

    private val binding: FragmentSelectFoodForMealBinding = DataBindingUtil.inflate<FragmentSelectFoodForMealBinding>(inflater, R.layout.fragment_select_food_for_meal, parent, false)

    init {
        setRootView(binding.root)
        initUI()
    }

    private val foodsListItemEventListener = object : FoodsRecyclerViewAdapter.ItemEventListener {
        override fun onItemClick(foodId: Int) {
            Toast.makeText(getContext(), "YAY", Toast.LENGTH_SHORT).show()
        }

        override fun onItemEdit(foodId: Int) {}
        override fun onItemDelete(foodId: Int) {}
    }

    private val foodsListAdapter = FoodsRecyclerViewAdapter(
        FoodsRecyclerViewAdapter.Config(
            false
        ),
        mutableListOf()
    ).apply { setOnItemEventListener(foodsListItemEventListener) }

    override fun makeDialog(): Dialog {
        return AlertDialog.Builder(getContext())
            .setView(getRootView())
            .setTitle(getString(R.string.select_food))
            .setPositiveButton(getString(android.R.string.ok)) { _, _ ->
                onPositiveButtonSelected()
            }
            .setNegativeButton(getString(android.R.string.cancel)) { _, _ -> }
            .show()
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

    private fun onPositiveButtonSelected() {

    }

    override fun bindFoods(foodsFromDatabase: List<Food>) {
        foodsListAdapter.updateFoods(foodsFromDatabase)
    }
}
