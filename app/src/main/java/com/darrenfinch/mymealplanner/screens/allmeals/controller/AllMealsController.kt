package com.darrenfinch.mymealplanner.screens.allmeals.controller

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.controllers.ControllerSavedState
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
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

    class SavedState : ControllerSavedState

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

    override fun onAddNewMealClicked() {
        screensNavigator.toMealFormScreen(Constants.NEW_ITEM_ID)
    }

    override fun onMealEdit(mealId: Int) {
        screensNavigator.toMealFormScreen(mealId)
    }

    override fun onMealDelete(mealId: Int) {
        runBlocking(backgroundContext) {
            deleteMealUseCase.deleteMeal(mealId)
        }
        getAllMealsAndBindToView()
    }

    override fun restoreState(state: ControllerSavedState) { }
    override fun getState(): ControllerSavedState {
        return SavedState()
    }
}
