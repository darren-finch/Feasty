package com.darrenfinch.mymealplanner.domain.allmeals.controller

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.allmeals.view.AllMealsViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.GetAllMealsUseCase
import com.darrenfinch.mymealplanner.model.data.entities.Meal

class AllMealsController(
    private val screensNavigator: ScreensNavigator,
    private val getAllMealsUseCase: GetAllMealsUseCase
) : AllMealsViewMvc.Listener {

    private lateinit var viewMvc: AllMealsViewMvc

    fun bindView(viewMvc: AllMealsViewMvc) {
        this.viewMvc = viewMvc
    }

    fun fetchMeals(viewLifecycleOwner: LifecycleOwner) {
        getAllMealsUseCase.fetchAllMeals().observe(viewLifecycleOwner, Observer { newMeals ->
            viewMvc.bindMeals(newMeals)
        })
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    override fun addNewMealClicked() {
        screensNavigator.navigateFromAllMealsScreenToSelectFoodForMealScreen(-1)
    }

    override fun onMealEdit(mealId: Int) {
        screensNavigator.navigateFromAllMealsScreenToSelectFoodForMealScreen(mealId)
    }

    override fun onMealDelete(meal: Meal) {

    }
}
