package com.darrenfinch.mymealplanner.domain.mealform.controller

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.misc.Constants
import com.darrenfinch.mymealplanner.common.misc.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.mealform.view.MealFormViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.GetMealUseCase
import com.darrenfinch.mymealplanner.domain.usecases.InsertMealUseCase
import com.darrenfinch.mymealplanner.domain.usecases.UpdateMealUseCase
import com.darrenfinch.mymealplanner.model.data.entities.Meal
import com.darrenfinch.mymealplanner.model.data.entities.MealFood

class MealFormController(
    private val viewModel: MealFormViewModel,
    private val insertMealUseCase: InsertMealUseCase,
    private val getMealUseCase: GetMealUseCase,
    private val screensNavigator: ScreensNavigator,
    private val newMealFood: MealFood?,
    private val currentMeal: Meal?
) : MealFormViewMvc.Listener {
    private lateinit var viewMvc: MealFormViewMvc

    fun bindView(viewMvc: MealFormViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    fun onViewCreated(mealId: Int, viewLifecycleOwner: LifecycleOwner) {
        if(isEditingExistingMeal(mealId) && isEditingMealForTheFirstTime()) {
            getMealUseCase.getMeal(mealId).observe(viewLifecycleOwner, Observer {
                viewModel.setObservableMeal(it)
                bindObservableMealToView()
            })
        }
        else {
            addNewMealFoodToCurrentMeal()
            bindObservableMealToView()
        }
    }

    private fun isEditingExistingMeal(mealId: Int) = mealId != Constants.DEFAULT_INVALID_MEAL_ID
    private fun isEditingMealForTheFirstTime() = currentMeal == null && newMealFood == null

    private fun bindObservableMealToView() {
        viewMvc.bindMealDetails(viewModel.getObservableMeal())
    }

    private fun addNewMealFoodToCurrentMeal() {
        if (currentMeal != null && newMealFood != null) {
            val updatedMeal =
                Meal(
                    currentMeal.id,
                    currentMeal.title,
                    currentMeal.foods.toMutableList().apply {
                        add(newMealFood)
                    })
            viewModel.setObservableMeal(updatedMeal)
        }
    }

    override fun addNewFoodButtonClicked() {
        screensNavigator.navigateToSelectFoodForMealScreen(viewModel.getObservableMeal().get())
    }

    override fun doneButtonClicked() {
        screensNavigator.navigateToAllMealsScreen()
        insertMealUseCase.insertMeal(viewModel.getObservableMeal().get())
    }
}