package com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.controller

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.misc.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.view.SelectMealPlanMealViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.GetAllMealsUseCase
import com.darrenfinch.mymealplanner.domain.usecases.InsertMealPlanMealUseCase
import com.darrenfinch.mymealplanner.model.data.entities.Meal
import com.darrenfinch.mymealplanner.model.data.entities.MealPlanMeal

class SelectMealPlanMealController(
    private val mealPlanId: Int,
    private val getAllMealsUseCase: GetAllMealsUseCase,
    private val insertMealPlanMealUseCase: InsertMealPlanMealUseCase,
    private val screensNavigator: ScreensNavigator
) : SelectMealPlanMealViewMvc.Listener {

    private lateinit var viewMvc: SelectMealPlanMealViewMvc

    fun bindView(viewMvc: SelectMealPlanMealViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    fun fetchMeals(lifecycleOwner: LifecycleOwner) {
        getAllMealsUseCase.fetchAllMeals().observe(lifecycleOwner, Observer {
            viewMvc.bindMeals(it)
        })
    }

    override fun onMealSelected(meal: Meal) {
        insertMealPlanMealUseCase.insertMealPlanMeal(MealPlanMeal(id = 0, mealPlanId = mealPlanId, mealId = meal.id, title = meal.title, foods =
        meal.foods))
        screensNavigator.navigateFromSelectMealPlanMealScreenToMealPlanScreen()
    }
}