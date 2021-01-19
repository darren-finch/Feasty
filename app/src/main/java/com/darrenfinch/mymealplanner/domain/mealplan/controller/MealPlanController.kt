package com.darrenfinch.mymealplanner.domain.mealplan.controller

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.misc.Constants
import com.darrenfinch.mymealplanner.common.navigation.DialogsManager
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.controller.SelectMealPlanMealDialog.Companion.SELECTED_MEAL
import com.darrenfinch.mymealplanner.domain.mealplan.controller.MealPlanFragment.Companion.NUM_OF_MEAL_PLANS
import com.darrenfinch.mymealplanner.domain.mealplan.controller.MealPlanFragment.Companion.SELECTED_MEAL_PLAN_ID
import com.darrenfinch.mymealplanner.domain.mealplan.controller.MealPlanFragment.Companion.SELECTED_MEAL_PLAN_INDEX
import com.darrenfinch.mymealplanner.domain.mealplan.view.MealPlanViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.*
import com.darrenfinch.mymealplanner.model.data.entities.Meal
import com.darrenfinch.mymealplanner.model.data.entities.MealPlan
import com.darrenfinch.mymealplanner.model.data.entities.MealPlanMeal
import com.darrenfinch.mymealplanner.model.data.structs.MealPlanMacros
import com.darrenfinch.mymealplanner.model.helpers.MacroCalculator

class MealPlanController(
    private val getAllMealPlansUseCase: GetAllMealPlansUseCase,
    private val getMealsForMealPlanUseCase: GetMealsForMealPlanUseCase,
    private val insertMealPlanMealUseCase: InsertMealPlanMealUseCase,
    private val deleteMealPlanUseCase: DeleteMealPlanUseCase,
    private val deleteMealPlanMealUseCase: DeleteMealPlanMealUseCase,
    private val screensNavigator: ScreensNavigator,
    private val dialogsManager: DialogsManager
) : BaseController, MealPlanViewMvc.Listener {

    private var selectedMealPlanIndex = Constants.INVALID_INDEX
    private var selectedMealPlanId = Constants.INVALID_ID
    private var numOfMealPlans = 0

    val hasPrevMealPlanIndex: Boolean
        get() = selectedMealPlanIndex - 1 > Constants.INVALID_INDEX

    val hasNextMealPlanIndex: Boolean
        get() = selectedMealPlanIndex + 1 < numOfMealPlans

    private lateinit var viewMvc: MealPlanViewMvc

    fun bindView(viewMvc: MealPlanViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    fun onViewCreated(viewLifecycleOwner: LifecycleOwner) {
        fetchAllMealPlans(viewLifecycleOwner)
    }

    private fun fetchAllMealPlans(viewLifecycleOwner: LifecycleOwner) {
        getAllMealPlansUseCase.getMealPlans().observe(viewLifecycleOwner, Observer { mealPlans ->
            if (mealPlans.isEmpty()) {
                viewMvc.hideMealPlans()
            } else {
                viewMvc.showMealPlans()

                // TODO: Default meal plan should come from SharedPreferences or somewhere similar in the future
                if (selectedMealPlanIndex == Constants.INVALID_INDEX)
                    selectedMealPlanIndex = 0
                if (selectedMealPlanId == Constants.INVALID_ID)
                    selectedMealPlanId = mealPlans[selectedMealPlanIndex].id

                viewMvc.setSelectedMealPlanIndex(selectedMealPlanIndex)

                // TODO: Fetch meals for selected meal plan
            }
            viewMvc.bindMealPlans(mealPlans)
        })
    }

    private fun getMealsForSelectedMealPlan(viewLifecycleOwner: LifecycleOwner) {
        //TODO: viewLifecycleOwner might potentially go out of scope, and it may cause a memory leak. Remove reference asap.
        getMealsForMealPlanUseCase.getMealsForMealPlan(selectedMealPlanId)
            .observe(viewLifecycleOwner, Observer { mealPlanMeals ->
                viewMvc.bindMealPlanMeals(mealPlanMeals)
                // TODO: Calculate macros for meal plan
            })
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
        selectedMealPlanIndex = index

        // TODO: Get meals for meal plan with selectedMealPlanId
    }

    override fun onAddNewMealPlanClicked() {
        screensNavigator.navigateToMealPlanFormScreen()
    }

    override fun onDeleteMealPlanClicked() {
        if (selectedMealPlanId > Constants.INVALID_ID) {
            deleteMealPlanUseCase.deleteMealPlan(selectedMealPlanId)
            if (hasPrevMealPlanIndex) {
                // TODO: Remove meal plan from viewMvc and refresh meals for newly selected meal plan
            } else if (hasNextMealPlanIndex) {
                // TODO: Remove meal plan from viewMvc and refresh meals for newly selected meal plan
            }
        }
    }

    override fun onAddNewMealPlanMealClicked() {
        dialogsManager.showSelectMealPlanMealDialog()
    }

    override fun onDeleteMealPlanMealClicked(mealPlanMeal: MealPlanMeal) {
        deleteMealPlanMealUseCase.deleteMealPlanMeal(mealPlanMeal)
    }

    override fun setState(state: Bundle?) {
        selectedMealPlanIndex = state?.getInt(SELECTED_MEAL_PLAN_INDEX) ?: 0
        selectedMealPlanId = state?.getInt(SELECTED_MEAL_PLAN_ID) ?: 0
        numOfMealPlans = state?.getInt(NUM_OF_MEAL_PLANS) ?: 0
    }
    override fun getState(): Bundle {
        return Bundle().apply {
            putInt(SELECTED_MEAL_PLAN_INDEX, selectedMealPlanIndex)
            putInt(SELECTED_MEAL_PLAN_ID, selectedMealPlanId)
            putInt(NUM_OF_MEAL_PLANS, numOfMealPlans)
        }
    }
}