package com.darrenfinch.mymealplanner.domain.dailymealplan.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableInt
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.controllers.BaseFragment
import com.darrenfinch.mymealplanner.common.utils.Utils
import com.darrenfinch.mymealplanner.databinding.FragmentMealPlanBinding
import com.darrenfinch.mymealplanner.model.frontend.TotalsVsRequiredMacros

class MealPlanFragment : BaseFragment() {
//    private val adapter =
//        MealsRecyclerViewAdapter(
//            mutableListOf()
//        )

    private lateinit var binding: FragmentMealPlanBinding

    private var totalCalories = ObservableInt()
    private var totalProtein = ObservableInt()
    private var totalCarbohydrates = ObservableInt()
    private var totalFat = ObservableInt()

    //These required values can and will change of course.
    private var requiredCalories = ObservableInt(2776)
    private var requiredProtein = ObservableInt(150)
    private var requiredCarbohydrates = ObservableInt(295)
    private var requiredFat = ObservableInt(81)

    private var allMeals = Utils.createSampleMeals()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentMealPlanBinding>(inflater, R.layout.fragment_meal_plan, container, false).apply {
            calculateMealMacroNutrients()
            macroNutrients = TotalsVsRequiredMacros(
                totalCalories,
                totalProtein,
                totalFat,
                totalCarbohydrates,
                requiredCalories,
                requiredProtein,
                requiredFat,
                requiredCarbohydrates
            )
//            mealsRecyclerView.adapter = adapter
//            mealsRecyclerView.layoutManager = LinearLayoutManager(context)
//            mealsRecyclerView.addItemDecoration(
//                MarginItemDecoration(
//                    16
//                )
//            )
//            adapter.updateMeals(allMeals)
            deleteAllMeals.setOnClickListener {
                fragmentCompositionRoot.getDeleteAllMealsUseCase().deleteAllMeals()
            }
        }
        return binding.root
    }
    fun calculateMealMacroNutrients()
    {
        allMeals.forEach { meal -> totalCalories.set(totalCalories.get() + Utils.calculateTotalCalories(meal)) }
        allMeals.forEach { meal -> totalProtein.set(totalProtein.get() + Utils.calculateTotalCarbohydrates(meal)) }
        allMeals.forEach { meal -> totalCarbohydrates.set(totalCarbohydrates.get() + Utils.calculateTotalFats(meal)) }
        allMeals.forEach { meal -> totalFat.set(totalFat.get() + Utils.calculateTotalProteins(meal)) }
    }
}
