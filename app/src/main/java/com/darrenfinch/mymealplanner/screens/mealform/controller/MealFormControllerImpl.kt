package com.darrenfinch.mymealplanner.screens.mealform.controller

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.controllers.ControllerSavedState
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.EditMealFoodDialogEvent
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.BackPressListener
import com.darrenfinch.mymealplanner.common.navigation.ScreenDataReturnBuffer
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood
import com.darrenfinch.mymealplanner.meals.usecases.GetMealUseCase
import com.darrenfinch.mymealplanner.meals.usecases.UpsertMealUseCase
import com.darrenfinch.mymealplanner.screens.mealform.MealFormData
import com.darrenfinch.mymealplanner.screens.mealform.view.MealFormViewMvc
import com.darrenfinch.mymealplanner.screens.selectfoodformeal.controller.SelectFoodForMealFragment
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MealFormControllerImpl(
    private var screenData: MealFormData,
    private val upsertMealUseCase: UpsertMealUseCase,
    private val getMealUseCase: GetMealUseCase,
    private val screensNavigator: ScreensNavigator,
    private val screenDataReturnBuffer: ScreenDataReturnBuffer,
    private val dialogsManager: DialogsManager,
    private val dialogsEventBus: DialogsEventBus,
    private val backPressDispatcher: BackPressDispatcher,
    private val backgroundContext: CoroutineContext,
    private val uiContext: CoroutineContext
) : MealFormController, MealFormViewMvc.Listener, BackPressListener, DialogsEventBus.Listener {

    private sealed class ScreenState {
        object Loading : ScreenState()
        class HasData(val mealDetails: UiMeal) : ScreenState()
    }

    data class SavedState(val screenData: MealFormData, val hasLoadedMealDetails: Boolean) :
        ControllerSavedState

    private var mealIdArg = Constants.INVALID_ID

    private var hasLoadedMealDetails = false

    private lateinit var viewMvc: MealFormViewMvc

    private var getMealJob: Job? = null

    override fun bindView(viewMvc: MealFormViewMvc) {
        this.viewMvc = viewMvc
    }

    override fun onStart() {
        viewMvc.registerListener(this)
        dialogsEventBus.registerListener(this)
        backPressDispatcher.registerListener(this)

        addChosenFoodToMealIfExists()
    }

    private fun addChosenFoodToMealIfExists() {
        if (screenDataReturnBuffer.hasDataForToken(SelectFoodForMealFragment.ASYNC_COMPLETION_TOKEN)) {
            val selectedFood = screenDataReturnBuffer.getData(SelectFoodForMealFragment.ASYNC_COMPLETION_TOKEN) as UiFood
            screenData.addMealFood(selectedFood)
            viewMvc.bindMealDetails(screenData.getMealDetails())
        }
    }

    override fun onStop() {
        viewMvc.unregisterListener(this)
        dialogsEventBus.unregisterListener(this)
        backPressDispatcher.unregisterListener(this)
        getMealJob?.cancel()
    }

    override fun getMealDetails() {
        setState(ScreenState.Loading)
        if (hasLoadedMealDetails) {
            // view model is retained on config changes, so this essentially restores the view
            setState(ScreenState.HasData(screenData.getMealDetails()))
        } else {
            getMealJob = CoroutineScope(backgroundContext).launch {
                val mealDetails = getMealUseCase.getMeal(mealIdArg)
                withContext(uiContext) {
                    setState(ScreenState.HasData(mealDetails))
                }
            }
        }
    }

    private fun setState(screenState: ScreenState) {
        when (screenState) {
            is ScreenState.Loading -> {
                viewMvc.showProgressIndication()
            }
            is ScreenState.HasData -> {
                viewMvc.hideProgressIndication()
                screenData.bindMealDetails(screenState.mealDetails)
                viewMvc.bindMealDetails(screenState.mealDetails)
                hasLoadedMealDetails = true
            }
        }
    }

    override fun onMealFoodEdit(mealFood: UiMealFood, index: Int) {
        dialogsManager.showEditMealFoodDialog(mealFood, index)
    }

    override fun onMealFoodDelete(index: Int) {
        screenData.removeMealFood(index)
        viewMvc.bindMealDetails(screenData.getMealDetails())
    }

    override fun onAddNewFoodButtonClicked() {
        screensNavigator.toSelectFoodForMealScreen()
    }

    override fun onDoneButtonClicked() {
        runBlocking(backgroundContext) {
            upsertMealUseCase.upsertMeal(screenData.getMealDetails())
        }
        screensNavigator.navigateUp()
    }

    override fun onNavigateUp() {
        screensNavigator.navigateUp()
    }

    override fun onTitleChange(newTitle: String) {
        screenData.setTitle(newTitle)
    }

    override fun setArgs(mealId: Int) {
        this.mealIdArg = mealId
    }

    override fun restoreState(state: ControllerSavedState) {
        (state as SavedState).let {
            screenData = it.screenData
            hasLoadedMealDetails = it.hasLoadedMealDetails
        }
    }

    override fun getState(): ControllerSavedState {
        return SavedState(screenData, hasLoadedMealDetails)
    }

    override fun onBackPressed(): Boolean {
        screensNavigator.navigateUp()
        return true
    }

    override fun onDialogEvent(event: Any) {
        if (event is EditMealFoodDialogEvent.OnPositiveButtonClicked) {
            screenData.updateMealFood(event.selectedMealFoodResult, event.index)
            viewMvc.bindMealDetails(screenData.getMealDetails())
        }
    }
}