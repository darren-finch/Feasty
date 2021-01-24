package com.darrenfinch.mymealplanner.screens.allmeals.controller

import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.meals.usecases.DeleteMealUseCase
import com.darrenfinch.mymealplanner.meals.usecases.GetAllMealsUseCase
import com.darrenfinch.mymealplanner.screens.allmeals.view.AllMealsViewMvc
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AllMealsController(
    private val screensNavigator: ScreensNavigator,
    private val getAllMealsUseCase: GetAllMealsUseCase,
    private val deleteMealUseCase: DeleteMealUseCase,
    private val backgroundContext: CoroutineContext,
    private val uiContext: CoroutineContext
) : BaseController, AllMealsViewMvc.Listener {

    class SavedState : BaseController.BaseSavedState

    private lateinit var viewMvc: AllMealsViewMvc

    private var getAllMealsJob: Job? = null

    fun bindView(viewMvc: AllMealsViewMvc) {
        this.viewMvc = viewMvc
    }

    fun getAllMealsAndBindToView() {
        viewMvc.showProgressIndication()
        getAllMealsJob = CoroutineScope(backgroundContext).launch {
            val allMeals = getAllMealsUseCase.getAllMeals()
            withContext(uiContext) {
                viewMvc.hideProgressIndication()
                viewMvc.bindMeals(allMeals)
            }
        }
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
        getAllMealsJob?.cancel()
    }

    override fun addNewMealClicked() {
        screensNavigator.toMealFormScreen(-1)
    }

    override fun onMealEdit(mealId: Int) {
        screensNavigator.toMealFormScreen(mealId)
    }

    override fun onMealDelete(meal: UiMeal) {
        runBlocking(backgroundContext) {
            deleteMealUseCase.deleteMeal(meal.id)
        }
        getAllMealsAndBindToView()
    }

    override fun restoreState(state: BaseController.BaseSavedState) { }
    override fun getState(): BaseController.BaseSavedState {
        return SavedState()
    }
}
