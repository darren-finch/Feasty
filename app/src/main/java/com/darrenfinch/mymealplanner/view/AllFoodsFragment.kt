package com.darrenfinch.mymealplanner.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.darrenfinch.mymealplanner.adapters.MarginItemDecoration

import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.adapters.FoodsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.databinding.FragmentAllFoodsBinding
import com.darrenfinch.mymealplanner.viewmodels.AllFoodsViewModel

class AllFoodsFragment : Fragment()
{
    private val viewModel: AllFoodsViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
    }

    private lateinit var binding: FragmentAllFoodsBinding

    private val foodsListItemEventListener = object : FoodsRecyclerViewAdapter.ItemEventListener {
        override fun onItemClick(foodId: Int) {}

        override fun onItemEdit(foodId: Int) {
            navigateToAddEditFoodFragment(foodId)
        }
        override fun onItemDelete(foodId: Int) {
            deleteFood(foodId)
        }
    }
    private fun deleteFood(foodId: Int) {
        viewModel.deleteFood(foodId)
    }
    private fun navigateToAddEditFoodFragment(foodId: Int) {
        val directions = AllFoodsFragmentDirections.actionFoodsFragmentToAddEditFoodFragment(foodId)
        findNavController().navigate(directions)
    }

    private val foodsListAdapter = FoodsRecyclerViewAdapter(FoodsRecyclerViewAdapter.Config(), mutableListOf()).apply { setOnItemEventListener(foodsListItemEventListener) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentAllFoodsBinding>(inflater, R.layout.fragment_all_foods, container, false).apply {
            foodsRecyclerView.adapter = foodsListAdapter
            foodsRecyclerView.layoutManager = LinearLayoutManager(context)
            foodsRecyclerView.addItemDecoration(MarginItemDecoration(16))

            addNewFood.setOnClickListener {
                navigateToAddEditFoodFragment(-1)
            }
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
