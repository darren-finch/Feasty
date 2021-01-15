package com.darrenfinch.mymealplanner.domain.mealplan.controller

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.misc.Constants
import com.darrenfinch.mymealplanner.common.navigation.DialogsManager
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.controller.SelectMealPlanMealDialog.Companion.SELECTED_MEAL
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
) : BaseController, MealPlanViewMvc.Listener, DialogsManager.OnDialogEventListener {

    var selectedMealPlanIndex = Constants.INVALID_INDEX
    var selectedMealPlanId = Constants.INVALID_ID
    var numOfMealPlans = 0

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
        dialogsManager.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
        dialogsManager.unregisterListener(this)
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

    }

    override fun getState(): Bundle {
        return Bundle()
    }

    override fun onDialogStart(dialogTag: String) {}
    override fun onDialogDismiss(dialogTag: String) {}
    override fun onDialogFinish(dialogTag: String, results: Bundle) {
        val selectedMeal = results.getSerializable(SELECTED_MEAL) as Meal
        insertMealPlanMealUseCase.insertMealPlanMeal(
            MealPlanMeal(
                id = 0,
                mealPlanId = selectedMealPlanId,
                mealId = selectedMeal.id,
                title = selectedMeal.title,
                foods = selectedMeal.foods
            )
        )
        // TODO: Refresh meal plan meals
    }
}