package com.darrenfinch.mymealplanner.screens.mealplan.controller

import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.controllers.ControllerSavedState
import com.darrenfinch.mymealplanner.common.helpers.SharedPreferencesHelper
import com.darrenfinch.mymealplanner.common.helpers.ToastsHelper
import com.darrenfinch.mymealplanner.common.navigation.ScreenDataReturnBuffer
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlanMeal
import com.darrenfinch.mymealplanner.mealplans.usecases.*
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.screens.mealplan.controller.MealPlanFragment.Companion.SELECTED_MEAL_PLAN_INDEX
import com.darrenfinch.mymealplanner.screens.mealplan.view.MealPlanViewMvc
import com.darrenfinch.mymealplanner.screens.selectmealplanmeal.controller.SelectMealPlanMealFragment
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MealPlanControllerImpl(
    private var savableScreenData: MealPlanSavableData,
    private val screenStatePresenter: MealPlanScreenStatePresenter,
    private val refreshMealPlanScreenUseCase: RefreshMealPlanScreenUseCase,
    private val insertMealPlanMealUseCase: InsertMealPlanMealUseCase,
    private val deleteMealPlanUseCase: DeleteMealPlanUseCase,
    private val deleteMealPlanMealUseCase: DeleteMealPlanMealUseCase,
    private val screensNavigator: ScreensNavigator,
    private val screenDataReturnBuffer: ScreenDataReturnBuffer,
    private val toastsHelper: ToastsHelper,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val backgroundContext: CoroutineContext,
    private val uiContext: CoroutineContext
) : MealPlanController, MealPlanViewMvc.Listener {

    data class SavedState(val screenSavableData: MealPlanSavableData) : ControllerSavedState

    private lateinit var viewMvc: MealPlanViewMvc

    private var getAllMealPlansJob: Job? = null
    private var deleteMealPlanJob: Job? = null
    private var insertMealPlanMealJob: Job? = null
    private var deleteMealPlanMealJob: Job? = null

    override fun bindView(viewMvc: MealPlanViewMvc) {
        this.viewMvc = viewMvc
        screenStatePresenter.bindView(viewMvc)
    }

    override fun onStart() {
        viewMvc.registerListener(this)

        addChosenMealToMealPlanIfExists()
    }

    private fun addChosenMealToMealPlanIfExists() {
        if (screenDataReturnBuffer.hasDataForToken(SelectMealPlanMealFragment.ASYNC_COMPLETION_TOKEN)) {
            val selectedMeal =
                screenDataReturnBuffer.getData(SelectMealPlanMealFragment.ASYNC_COMPLETION_TOKEN) as UiMeal
            val newMealPlanMeal = UiMealPlanMeal(
                id = Constants.NEW_ITEM_ID,
                savableScreenData.getSelectedMealPlanId(),
                selectedMeal.id,
                selectedMeal.title,
                selectedMeal.foods
            )

            insertMealPlanMealJob = CoroutineScope(backgroundContext).launch {
                insertMealPlanMealUseCase.insertMealPlanMeal(newMealPlanMeal)
                refresh()
            }
        }
    }

    override fun onStop() {
        viewMvc.unregisterListener(this)
        getAllMealPlansJob?.cancel()
        deleteMealPlanJob?.cancel()
        insertMealPlanMealJob?.cancel()
        deleteMealPlanMealJob?.cancel()
    }

    override fun refresh() {
        getAllMealPlansJob?.cancel()
        getAllMealPlansJob = CoroutineScope(backgroundContext).launch {
            setScreenState(MealPlanScreenState.Loading)

            val result = refreshMealPlanScreenUseCase.refresh()

            withContext(uiContext) {
                when (result) {
                    is RefreshMealPlanScreenUseCase.Result.NoMealPlans -> {
                        setScreenState(MealPlanScreenState.NoMealPlans)
                    }
                    is RefreshMealPlanScreenUseCase.Result.HasMealPlansAndMealsForSelectedMealPlan -> {
                        setScreenState(
                            MealPlanScreenState.HasMealPlansAndMealsForSelectedMealPlan(
                                result.mealPlans,
                                result.mealsForSelectedMealPlan,
                                result.selectedMealPlanMacros,
                                result.selectedMealPlanIndex
                            )
                        )
                    }
                    is RefreshMealPlanScreenUseCase.Result.HasMealPlansButNoMealsForSelectedMealPlan -> {
                        setScreenState(
                            MealPlanScreenState.HasMealPlansButNoMealsForSelectedMealPlan(
                                result.mealPlans,
                                result.selectedMealPlanMacros,
                                result.selectedMealPlanIndex
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onMealPlanSelectedByUser(index: Int) {
        sharedPreferencesHelper.putInt(SELECTED_MEAL_PLAN_INDEX, index)
        savableScreenData.setSelectedMealPlanIndex(index)
        refresh()
    }

    override fun onAddNewMealPlanClicked() {
        screensNavigator.toMealPlanFormScreen()
    }

    override fun onDeleteMealPlanClicked() {
        deleteMealPlanJob = CoroutineScope(backgroundContext).launch {
            deleteMealPlanUseCase.deleteMealPlan(savableScreenData.getSelectedMealPlanId())

            withContext(uiContext) {
                savableScreenData.moveSelectedMealPlanIndex()
                setSelectedMealPlanIndex(savableScreenData.getSelectedMealPlanIndex())
            }

            refresh()
        }
    }

    override fun onAddNewMealPlanMealClicked() {
        if (savableScreenData.getSelectedMealPlanId() == Constants.INVALID_ID) {
            toastsHelper.showShortMsg(R.string.no_meal_plan_selected_cant_add_meal)
        } else {
            screensNavigator.toSelectMealPlanMealScreen()
        }
    }

    override fun onDeleteMealPlanMealClicked(mealPlanMealId: Int) {
        deleteMealPlanMealJob = CoroutineScope(backgroundContext).launch {
            deleteMealPlanMealUseCase.deleteMealPlanMeal(mealPlanMealId)
            refresh()
        }
    }

    override fun restoreState(state: ControllerSavedState) {
        (state as SavedState).let {
            savableScreenData = it.screenSavableData
        }
    }

    override fun getState(): ControllerSavedState {
        return SavedState(savableScreenData)
    }

    private suspend fun setScreenState(screenState: MealPlanScreenState) {
        withContext(uiContext) {
            // Present state to view
            screenStatePresenter.presentState(screenState)
            setControllerState(screenState)
        }
    }

    private fun setControllerState(screenState: MealPlanScreenState) {
        when (screenState) {
            is MealPlanScreenState.NoMealPlans -> {
                savableScreenData.setInitialMealPlans(emptyList())
            }
            is MealPlanScreenState.HasMealPlansAndMealsForSelectedMealPlan -> {
                screenState.let {
                    savableScreenData.setInitialMealPlans(it.mealPlans)
                    setSelectedMealPlanIndex(it.selectedMealPlanIndex)
                }
            }
            is MealPlanScreenState.HasMealPlansButNoMealsForSelectedMealPlan -> {
                screenState.let {
                    savableScreenData.setInitialMealPlans(it.mealPlans)
                    setSelectedMealPlanIndex(it.selectedMealPlanIndex)
                }
            }
            else -> {
                // nothing to handle ...
            }
        }
    }

    private fun setSelectedMealPlanIndex(index: Int) {
        sharedPreferencesHelper.putInt(SELECTED_MEAL_PLAN_INDEX, index)
        savableScreenData.setSelectedMealPlanIndex(index)
        viewMvc.setSelectedMealPlanIndex(index)
    }
}