package com.darrenfinch.mymealplanner.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.adapters.MarginItemDecoration
import com.darrenfinch.mymealplanner.adapters.MealsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.databinding.FragmentAllMealsBinding
import com.darrenfinch.mymealplanner.viewmodels.AllMealsViewModel

class AllMealsFragment : Fragment() {
    private val allMealsViewModel: AllMealsViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
    }
    private val adapter = MealsRecyclerViewAdapter(mutableListOf())

    private lateinit var binding: FragmentAllMealsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentAllMealsBinding>(
            inflater,
            R.layout.fragment_all_meals,
            container,
            false
        ).apply {
            mealsRecyclerView.adapter = adapter
            mealsRecyclerView.layoutManager = LinearLayoutManager(context)
            mealsRecyclerView.addItemDecoration(MarginItemDecoration(16))

            addNewMeal.setOnClickListener { addNewMeal() }
        }

        allMealsViewModel.fetchAllMeals().observe(viewLifecycleOwner, Observer { meals ->
            meals?.let {
                adapter.updateMeals(it)
            }
        })

        return binding.root
    }

    fun addNewMeal() {
        findNavController().navigate(R.id.addEditMealFragment)
    }
}
