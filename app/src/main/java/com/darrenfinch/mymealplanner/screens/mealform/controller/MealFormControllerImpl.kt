package com.darrenfinch.mymealplanner.screens.mealform.controller

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.dialogs.DialogResult
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodformeal.SelectFoodForMealDialogEvent
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.SelectFoodQuantityDialogEvent
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.controller.SelectFoodQuantityDialog.Companion.DESIRED_SERVING_SIZE_RESULT
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.controller.SelectFoodQuantityDialog.Companion.FOOD_ID_RESULT
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.controller.SelectFoodQuantityDialog.Companion.SELECTED_FOOD_RESULT
import com.darrenfinch.mymealplanner.common.misc.ControllerSavedState
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.BackPressListener
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.meals.usecases.GetMealUseCase
import com.darrenfinch.mymealplanner.meals.usecases.InsertMealUseCase
import com.darrenfinch.mymealplanner.meals.usecases.UpdateMealUseCase
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.screens.mealform.MealFormVm
import com.darrenfinch.mymealplanner.screens.mealform.view.MealFormViewMvc
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
) : MealFormController, MealFormViewMvc.Listener, BackPressListener, DialogsEventBus.Listener {

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
    }

    override fun onStop() {
        viewMvc.unregisterListener(this)
        dialogsEventBus.unregisterListener(this)
        backPressDispatcher.unregisterListener(this)
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

    override fun onAddNewFoodButtonClicked() {
        dialogsManager.showSelectFoodForMealScreenDialog()
    }

    override fun onDoneButtonClicked() {
        runBlocking(backgroundContext) {
            if (mealIdArg == Constants.INVALID_ID)
                insertMealUseCase.insertMeal(viewModel.getMealDetails())
            else
                updateMealUseCase.updateMeal(viewModel.getMealDetails())
        }
        screensNavigator.goBack()
    }

    override fun onNavigateUp() {
        screensNavigator.goBack()
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
        screensNavigator.goBack()
        return true
    }

    override fun onDialogEvent(event: Any, result: DialogResult?) {
        result?.let {
            if (event == SelectFoodForMealDialogEvent.ON_FOOD_CHOSEN) {
                dialogsManager.showSelectFoodQuantityDialog(result.getInt(FOOD_ID_RESULT))
            } else if (event == SelectFoodQuantityDialogEvent.ON_DESIRED_FOOD_SERVING_SIZE_CHOSEN) {
                val selectedFood = result.getSerializable(SELECTED_FOOD_RESULT) as UiFood
                val selectedFoodQuantity =
                    result.getSerializable(DESIRED_SERVING_SIZE_RESULT) as PhysicalQuantity
                viewModel.addMealFood(selectedFood, selectedFoodQuantity)
                setState(ScreenState.HasData(viewModel.getMealDetails()))
            }
        }
    }
}