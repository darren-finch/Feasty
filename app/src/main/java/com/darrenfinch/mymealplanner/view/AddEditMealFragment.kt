package com.darrenfinch.mymealplanner.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.databinding.FragmentAddEditMealBinding

class AddEditMealFragment : Fragment() {

    private lateinit var binding: FragmentAddEditMealBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentAddEditMealBinding>(inflater, R.layout.fragment_add_edit_meal, container, false).apply {
            addNewFood.setOnClickListener {
                openSelectFoodForMealFragment()
            }
        }
        return binding.root
    }
    private fun openSelectFoodForMealFragment() {
        val selectFoodForMealDialog = SelectFoodForMealDialog()
        selectFoodForMealDialog.show(childFragmentManager, "SelectFoodForMealDialog")
    }
}
