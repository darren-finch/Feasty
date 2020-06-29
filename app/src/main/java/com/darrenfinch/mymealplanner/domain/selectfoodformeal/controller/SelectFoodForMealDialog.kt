package com.darrenfinch.mymealplanner.domain.selectfoodformeal.controller

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.reusable.foodrecyclerviewadapter.FoodsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.common.reusable.recyclerviewitemdecorations.MarginItemDecoration
import com.darrenfinch.mymealplanner.databinding.FragmentSelectFoodForMealBinding

class SelectFoodForMealDialog : DialogFragment() {

    private val viewModel: SelectFoodForMealViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
    }

    private lateinit var binding: FragmentSelectFoodForMealBinding

    private val foodsListItemEventListener = object : FoodsRecyclerViewAdapter.ItemEventListener {
        override fun onItemClick(foodId: Int) {
            Toast.makeText(context, "YAY", Toast.LENGTH_SHORT).show()
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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_select_food_for_meal,
            null,
            false
        )

        return makeDialog()
    }
    private fun makeDialog(): AlertDialog {
        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setTitle(getString(R.string.select_food))
            .setPositiveButton(getString(android.R.string.ok)) { _, _ ->
                onPositiveButtonSelected()
            }
            .setNegativeButton(getString(android.R.string.cancel)) { _, _ -> }
            .show()
    }
    private fun onPositiveButtonSelected() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentSelectFoodForMealBinding>(inflater, R.layout.fragment_select_food_for_meal, container, false).apply {
            foodsRecyclerView.adapter = foodsListAdapter
            foodsRecyclerView.layoutManager = LinearLayoutManager(context)
            foodsRecyclerView.addItemDecoration(
                MarginItemDecoration(
                    16
                )
            )
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.fetchAllFoods().observe(viewLifecycleOwner, Observer { newFoods ->
            newFoods?.let {
                foodsListAdapter.updateFoods(newFoods)
            }
        })
    }
}
