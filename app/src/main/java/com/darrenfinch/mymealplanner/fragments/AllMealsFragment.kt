package com.darrenfinch.mymealplanner.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.darrenfinch.mymealplanner.MarginItemDecoration

import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.adapters.MealsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.databinding.FragmentAllMealsBinding
import com.darrenfinch.mymealplanner.utils.Utils

class AllMealsFragment : Fragment()
{
    private lateinit var binding: FragmentAllMealsBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentAllMealsBinding>(inflater, R.layout.fragment_all_meals, container,false).apply{
            mealsRecyclerView.adapter = MealsRecyclerViewAdapter(Utils.createSampleMeals())
            mealsRecyclerView.layoutManager = LinearLayoutManager(context)
            mealsRecyclerView.addItemDecoration(MarginItemDecoration(16))
        }
        return binding.root
    }
}
