package com.darrenfinch.mymealplanner.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableInt
import androidx.recyclerview.widget.LinearLayoutManager
import com.darrenfinch.mymealplanner.MarginItemDecoration
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.adapters.MealsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.data.frontend.TotalsVsRequiredMacros
import com.darrenfinch.mymealplanner.databinding.FragmentMealPlanBinding
import com.darrenfinch.mymealplanner.utils.Utils

class MealPlanFragment : Fragment()
{
    private lateinit var binding: FragmentMealPlanBinding

    private var totalCalories = ObservableInt()
    private var totalProtein = ObservableInt()
    private var totalCarbohydrates = ObservableInt()
    private var totalFat = ObservableInt()
    private var requiredCalories = ObservableInt(2776)
    private var requiredProtein = ObservableInt(150)
    private var requiredCarbohydrates = ObservableInt(295)
    private var requiredFat = ObservableInt(81)

    private var allMeals = Utils.createSampleMeals()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentMealPlanBinding>(inflater, R.layout.fragment_meal_plan, container,false).apply {
            calculateMealMacroNutrients()
            macroNutrients = TotalsVsRequiredMacros(totalCalories, totalProtein, totalFat, totalCarbohydrates, requiredCalories, requiredProtein, requiredFat, requiredCarbohydrates)
            mealsRecyclerView.adapter = MealsRecyclerViewAdapter(allMeals)
            mealsRecyclerView.layoutManager = LinearLayoutManager(context)
            mealsRecyclerView.addItemDecoration(MarginItemDecoration(16))
        }
        return binding.root
    }
    fun calculateMealMacroNutrients()
    {
        allMeals.forEach { meal -> totalCalories.set(totalCalories.get() + meal.calculateTotalCalories()) }
        allMeals.forEach { meal -> totalProtein.set(totalProtein.get() + meal.calculateTotalProtein()) }
        allMeals.forEach { meal -> totalCarbohydrates.set(totalCarbohydrates.get() + meal.calculateTotalCarbohydrates()) }
        allMeals.forEach { meal -> totalFat.set(totalFat.get() + meal.calculateTotalFat()) }
    }
}
