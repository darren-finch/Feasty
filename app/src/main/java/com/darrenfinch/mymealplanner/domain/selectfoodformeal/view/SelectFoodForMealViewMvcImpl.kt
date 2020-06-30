package com.darrenfinch.mymealplanner.domain.selectfoodformeal.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.reusable.foodrecyclerviewadapter.FoodsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentSelectFoodForMealBinding
import com.darrenfinch.mymealplanner.model.data.Food

class SelectFoodForMealViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : BaseObservableViewMvc<SelectFoodForMealViewMvc.Listener>(), SelectFoodForMealViewMvc {

    private val binding: FragmentSelectFoodForMealBinding = DataBindingUtil.inflate(
        inflater,
        R.layout.fragment_select_food_for_meal,
        parent,
        false
    )

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

    fun makeDialog(): AlertDialog {
        return AlertDialog.Builder(getContext())
            .setView(getRootView())
            .setTitle(getString(R.string.select_food))
            .setPositiveButton(getString(android.R.string.ok)) { _, _ ->
                onPositiveButtonSelected()
            }
            .setNegativeButton(getString(android.R.string.cancel)) { _, _ -> }
            .show()
    }

    private fun onPositiveButtonSelected() {

    }

    override fun bindFoods(foodsFromDatabase: List<Food>) {
        foodsListAdapter.updateFoods(foodsFromDatabase)
    }
}
