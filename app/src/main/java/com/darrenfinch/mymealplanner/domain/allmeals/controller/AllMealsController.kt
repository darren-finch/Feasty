package com.darrenfinch.mymealplanner.domain.allmeals.controller

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.allmeals.view.AllMealsViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.DeleteMealUseCase
import com.darrenfinch.mymealplanner.domain.usecases.GetAllMealsUseCase
import com.darrenfinch.mymealplanner.model.data.entities.Meal

class AllMealsController(
    private val screensNavigator: ScreensNavigator,
    private val getAllMealsUseCase: GetAllMealsUseCase,
    private val deleteMealUseCase: DeleteMealUseCase
) : BaseController, AllMealsViewMvc.Listener {

    class SavedState : BaseController.BaseSavedState

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
        screensNavigator.navigateToMealFormScreen(-1)
    }

    override fun onMealEdit(mealId: Int) {
        screensNavigator.navigateToMealFormScreen(mealId)
    }

    override fun onMealDelete(meal: Meal) {
        deleteMealUseCase.deleteMeal(meal.id)
    }

    override fun restoreState(state: BaseController.BaseSavedState) { }
    override fun getState(): BaseController.BaseSavedState {
        return SavedState()
    }
}
