package com.darrenfinch.mymealplanner.domain.selectmealfoodquantity.controller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.darrenfinch.mymealplanner.R

/**
 * A simple [Fragment] subclass.
 */
class SelectMealFoodQuantityDialog : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_meal_food_quantity_dialog, container, false)
    }

}
