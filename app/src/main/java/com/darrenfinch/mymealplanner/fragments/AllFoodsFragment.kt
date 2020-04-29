package com.darrenfinch.mymealplanner.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.darrenfinch.mymealplanner.MarginItemDecoration

import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.adapters.FoodsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.databinding.FragmentAllFoodsBinding
import com.darrenfinch.mymealplanner.utils.Utils

class AllFoodsFragment : Fragment()
{
    private lateinit var binding: FragmentAllFoodsBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentAllFoodsBinding>(inflater, R.layout.fragment_all_foods, container, false).apply {
            foodsRecyclerView.adapter = FoodsRecyclerViewAdapter(Utils.createSampleFoods())
            foodsRecyclerView.layoutManager = LinearLayoutManager(context)
            foodsRecyclerView.addItemDecoration(MarginItemDecoration(16))
        }
        return binding.root
    }
}
