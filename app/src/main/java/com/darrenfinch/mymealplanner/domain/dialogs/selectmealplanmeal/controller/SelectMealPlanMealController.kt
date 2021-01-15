package com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.controller

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.controllers.BaseDialog
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.controller.SelectMealPlanMealDialog.Companion.MEAL_PLAN_ID
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.view.SelectMealPlanMealViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.GetAllMealsUseCase
import com.darrenfinch.mymealplanner.domain.usecases.InsertMealPlanMealUseCase
import com.darrenfinch.mymealplanner.model.data.entities.Meal
import com.darrenfinch.mymealplanner.model.data.entities.MealPlanMeal

class SelectMealPlanMealController(
    private val getAllMealsUseCase: GetAllMealsUseCase,
    private val insertMealPlanMealUseCase: InsertMealPlanMealUseCase
) : BaseController, SelectMealPlanMealViewMvc.Listener {

    private var mealPlanId = -1
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
        insertMealPlanMealUseCase.insertMealPlanMeal(
            MealPlanMeal(
                id = 0,
                mealPlanId = mealPlanId,
                mealId = meal.id,
                title = meal.title,
                foods = meal.foods
            )
        )
    }

    override fun setState(state: Bundle?) {
        mealPlanId = state?.getInt(MEAL_PLAN_ID) ?: -1
    }
    override fun getState(): Bundle {
        return Bundle().apply {
            putInt(MEAL_PLAN_ID, mealPlanId)
        }
    }
}