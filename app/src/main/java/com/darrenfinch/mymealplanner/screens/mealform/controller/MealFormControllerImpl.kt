package com.darrenfinch.mymealplanner.screens.mealform.controller

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.controllers.ControllerSavedState
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.EditMealFoodDialogEvent
import com.darrenfinch.mymealplanner.common.logs.getClassTag
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.BackPressListener
import com.darrenfinch.mymealplanner.common.navigation.ScreenResult
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood
import com.darrenfinch.mymealplanner.meals.usecases.GetMealUseCase
import com.darrenfinch.mymealplanner.meals.usecases.InsertMealUseCase
import com.darrenfinch.mymealplanner.meals.usecases.UpdateMealUseCase
import com.darrenfinch.mymealplanner.screens.mealform.MealFormVm
import com.darrenfinch.mymealplanner.screens.mealform.view.MealFormViewMvc
import com.darrenfinch.mymealplanner.screens.selectfoodformeal.controller.SelectFoodForMealFragment
import com.darrenfinch.mymealplanner.screens.selectfoodformeal.controller.SelectFoodForMealFragment.Companion.SELECTED_FOOD_RESULT
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MealFormControllerImpl(
    private var viewModel: MealFormVm,
    private val insertMealUseCase: InsertMealUseCase,
    private val updateMealUseCase: UpdateMealUseCase,
    private val getMealUseCase: GetMealUseCase,
    private val screensNavigator: ScreensNavigator,
    private val dialogsManager: DialogsManager,
    private val dialogsEventBus: DialogsEventBus,
    private val backPressDispatcher: BackPressDispatcher,
    private val backgroundContext: CoroutineContext,
    private val uiContext: CoroutineContext
) : MealFormController, MealFormViewMvc.Listener, BackPressListener, DialogsEventBus.Listener,
    ScreensNavigator.Listener {

    private sealed class ScreenState {
        object Loading : ScreenState()
        class HasData(val mealDetails: UiMeal) : ScreenState()
    }

    data class SavedState(val viewModel: MealFormVm, val hasLoadedMealDetails: Boolean) :
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
        screensNavigator.registerListener(this)
    }

    override fun onStop() {
        viewMvc.unregisterListener(this)
        dialogsEventBus.unregisterListener(this)
        backPressDispatcher.unregisterListener(this)
        screensNavigator.unregisterListener(this)
        getMealJob?.cancel()
    }

    override fun getMealDetails() {
        setState(ScreenState.Loading)
        if(hasLoadedMealDetails) {
            // view model is retained on config changes, so this essentially restores the view
            setState(ScreenState.HasData(viewModel.getMealDetails()))
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
                viewModel.bindMealDetails(screenState.mealDetails)
                viewMvc.bindMealDetails(screenState.mealDetails)
                hasLoadedMealDetails = true
            }
        }
    }

    override fun onMealFoodEdit(mealFood: UiMealFood) {
        dialogsManager.showEditMealFoodDialog(mealFood)
    }

    override fun onMealFoodDelete(id: Int) {
        viewModel.removeMealFood(id)
        viewMvc.bindMealDetails(viewModel.getMealDetails())
    }

    override fun onAddNewFoodButtonClicked() {
        screensNavigator.toSelectFoodForMealScreen()
    }

    override fun onDoneButtonClicked() {
        runBlocking(backgroundContext) {
            if (mealIdArg == Constants.NEW_ITEM_ID)
                insertMealUseCase.insertMeal(viewModel.getMealDetails())
            else
                updateMealUseCase.updateMeal(viewModel.getMealDetails())
        }
        screensNavigator.navigateUp()
    }

    override fun onNavigateUp() {
        screensNavigator.navigateUp()
    }

    override fun onTitleChange(newTitle: String) {
        viewModel.setTitle(newTitle)
    }

    override fun setArgs(mealId: Int) {
        this.mealIdArg = mealId
    }

    override fun restoreState(state: ControllerSavedState) {
        (state as SavedState).let {
            viewModel = it.viewModel
            hasLoadedMealDetails = it.hasLoadedMealDetails
        }
    }

    override fun getState(): ControllerSavedState {
        return SavedState(viewModel, hasLoadedMealDetails)
    }

    override fun onBackPressed(): Boolean {
        screensNavigator.navigateUp()
        return true
    }

    override fun onDialogEvent(event: Any) {
        if(event is EditMealFoodDialogEvent.OnPositiveButtonClicked) {
            viewModel.updateMealFood(event.mealFoodResult)
            setState(ScreenState.HasData(viewModel.getMealDetails()))
        }
    }

    override fun onGoBackWithResult(result: ScreenResult) {
        if(result.tag == SelectFoodForMealFragment.getClassTag()) {
            val selectedFood = result.getSerializable(SELECTED_FOOD_RESULT) as UiFood
            viewModel.addMealFood(selectedFood)
            setState(ScreenState.HasData(viewModel.getMealDetails()))
        }
    }
}