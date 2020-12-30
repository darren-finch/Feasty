package com.darrenfinch.mymealplanner.domain.mealplan.controller

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.mealplan.view.MealPlanViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.DeleteMealPlanMealUseCase
import com.darrenfinch.mymealplanner.domain.usecases.DeleteMealPlanUseCase
import com.darrenfinch.mymealplanner.domain.usecases.GetAllMealPlansUseCase
import com.darrenfinch.mymealplanner.domain.usecases.GetMealsForMealPlanUseCase
import com.darrenfinch.mymealplanner.model.data.entities.MealPlan
import com.darrenfinch.mymealplanner.model.data.entities.MealPlanMeal
import com.darrenfinch.mymealplanner.model.data.structs.MealPlanMacros
import com.darrenfinch.mymealplanner.model.helpers.MacroCalculator

class MealPlanController(
    private val getAllMealPlansUseCase: GetAllMealPlansUseCase,
    private val getMealsForMealPlanUseCase: GetMealsForMealPlanUseCase,
    private val deleteMealPlanUseCase: DeleteMealPlanUseCase,
    private val deleteMealPlanMealUseCase: DeleteMealPlanMealUseCase,
    private val viewModel: MealPlanViewModel,
    private val screensNavigator: ScreensNavigator
) :
    MealPlanViewMvc.Listener {
    private lateinit var viewMvc: MealPlanViewMvc
    private var viewLifecycleOwner: LifecycleOwner? = null

    fun bindView(viewMvc: MealPlanViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
        viewLifecycleOwner = null
    }

    fun onViewCreated(viewLifecycleOwner: LifecycleOwner) {
        this.viewLifecycleOwner = viewLifecycleOwner
        fetchAllMealPlans()
    }

    private fun fetchAllMealPlans() {
        if(viewLifecycleOwner != null) {
            getAllMealPlansUseCase.getMealPlans().observe(viewLifecycleOwner!!, Observer { mealPlans ->
                viewModel.allMealPlans = mealPlans
                if (mealPlans.isEmpty()) {
                    viewMvc.hideMealPlans()
                } else {
                    viewMvc.showMealPlans()
                    //TODO: This should come from SharedPrefs or somewhere similar in the future.
                    viewModel.selectedMealPlanIndex = 0
                    viewModel.selectedMealPlan = mealPlans[0]
                    viewMvc.setSelectedMealPlanIndex(viewModel.selectedMealPlanIndex)
                    getMealsForSelectedMealPlan()
                }
                viewMvc.bindMealPlans(mealPlans)
            })
        }
    }

    private fun getMealsForSelectedMealPlan() {
        //TODO: Maybe make this code a little more thread-safe?
        if(viewModel.selectedMealPlan != null && viewLifecycleOwner != null) {
            getMealsForMealPlanUseCase.getMealsForMealPlan(viewModel.selectedMealPlan!!.id)
                .observe(viewLifecycleOwner!!, Observer { mealPlanMeals ->
                    viewMvc.bindMealPlanMeals(mealPlanMeals)
                    viewMvc.bindMealPlanMacros(
                        calculateMacroNutrientsForMealPlan(
                            mealPlanMeals,
                            viewModel.selectedMealPlan!!
                        )
                    )
                })
        }
    }

    private fun calculateMacroNutrientsForMealPlan(
        mealsInMealPlan: List<MealPlanMeal>,
        mealPlan: MealPlan
    ): MealPlanMacros {
        val totalCalories = mealsInMealPlan.sumBy { meal -> MacroCalculator.calculateTotalCalories(meal) }
        val totalProtein = mealsInMealPlan.sumBy { meal -> MacroCalculator.calculateTotalProteins(meal) }
        val totalCarbohydrates =
            mealsInMealPlan.sumBy { meal -> MacroCalculator.calculateTotalCarbohydrates(meal) }
        val totalFat = mealsInMealPlan.sumBy { meal -> MacroCalculator.calculateTotalFats(meal) }

        return MealPlanMacros(
            totalCalories = totalCalories,
            totalProtein = totalProtein,
            totalFat = totalFat,
            totalCarbohydrates = totalCarbohydrates,
            requiredCalories = mealPlan.requiredCalories,
            requiredProtein = mealPlan.requiredProtein,
            requiredFat = mealPlan.requiredFat,
            requiredCarbohydrates = mealPlan.requiredCarbohydrates
        )
    }

    override fun onMealPlanSelected(index: Int) {
        viewModel.selectedMealPlanIndex = index
        viewModel.selectedMealPlan = viewModel.allMealPlans[index]
        getMealsForSelectedMealPlan()
    }

    override fun onAddNewMealPlanClicked() {
        screensNavigator.navigateFromMealPlanScreenToMealPlanFormScreen()
    }

    override fun onDeleteMealPlanClicked() {
        if(viewModel.selectedMealPlan != null) {
            deleteMealPlanUseCase.deleteMealPlan(viewModel.selectedMealPlan!!)
            if(viewModel.hasPrevMealPlanIndex()) {
                viewModel.selectedMealPlan = viewModel.allMealPlans[viewModel.selectedMealPlanIndex - 1]
                getMealsForSelectedMealPlan()
            }
            else if(viewModel.hasNextMealPlanIndex()) {
                viewModel.selectedMealPlan = viewModel.allMealPlans[viewModel.selectedMealPlanIndex + 1]
                getMealsForSelectedMealPlan()
            }
        }
    }

    override fun onAddNewMealPlanMealClicked() {
        viewModel.selectedMealPlan?.let {
            screensNavigator.navigateFromMealPlanScreenToSelectMealPlanMealScreen(it.id)
        }
    }

    override fun onDeleteMealPlanMealClicked(mealPlanMeal: MealPlanMeal) {
        deleteMealPlanMealUseCase.deleteMealPlanMeal(mealPlanMeal)
    }
}