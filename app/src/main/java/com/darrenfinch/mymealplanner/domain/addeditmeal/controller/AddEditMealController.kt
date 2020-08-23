package com.darrenfinch.mymealplanner.domain.addeditmeal.controller

import com.darrenfinch.mymealplanner.common.Constants
import com.darrenfinch.mymealplanner.common.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.addeditmeal.view.AddEditMealViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.InsertMealUseCase
import com.darrenfinch.mymealplanner.model.data.entities.Meal
import com.darrenfinch.mymealplanner.model.data.entities.MealFood

class AddEditMealController(
    private val viewModel: AddEditMealViewModel,
    private val insertMealUseCase: InsertMealUseCase,
    private val screensNavigator: ScreensNavigator,
    private val newMealFood: MealFood?,
    private val currentMeal: Meal?
) : AddEditMealViewMvc.Listener {
    private lateinit var viewMvc: AddEditMealViewMvc

    fun bindView(viewMvc: AddEditMealViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    fun onViewCreated(mealId: Int) {
        if(!isEditingFoodForTheFirstTime(mealId)) {
            addNewMealFoodToCurrentMeal()
            bindObservableMealToView()
        }
        else {
            //Asynchronously fetch meal from repository
            //When meal is fetched:
            //Bind observable meal to view
        }
    }

    private fun isEditingFoodForTheFirstTime(mealId: Int) = mealId != Constants.DEFAULT_INVALID_MEAL_ID && currentMeal == null && newMealFood == null

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