package com.darrenfinch.mymealplanner.screens.mealform.controller

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.dialogs.DialogResult
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodformeal.SelectFoodForMealDialogEvent
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.SelectFoodQuantityDialogEvent
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.controller.SelectFoodQuantityDialog.Companion.FOOD_ID_RESULT
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.controller.SelectFoodQuantityDialog.Companion.DESIRED_SERVING_SIZE_RESULT
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.controller.SelectFoodQuantityDialog.Companion.SELECTED_FOOD_RESULT
import com.darrenfinch.mymealplanner.common.misc.ControllerSavedState
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.BackPressListener
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.meals.usecases.GetMealUseCase
import com.darrenfinch.mymealplanner.meals.usecases.InsertMealUseCase
import com.darrenfinch.mymealplanner.meals.usecases.UpdateMealUseCase
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.screens.mealform.MealFormVm
import com.darrenfinch.mymealplanner.screens.mealform.view.MealFormViewMvc
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MealFormController(
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
) : BaseController, MealFormViewMvc.Listener, BackPressListener, DialogsEventBus.Listener {

    data class SavedState(val viewModel: MealFormVm) :
        ControllerSavedState

    private var mealIdArg = Constants.INVALID_ID

    private lateinit var viewMvc: MealFormViewMvc

    private var getMealJob: Job? = null

    fun bindView(viewMvc: MealFormViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
        dialogsEventBus.registerListener(this)
        backPressDispatcher.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
        dialogsEventBus.unregisterListener(this)
        backPressDispatcher.unregisterListener(this)
        getMealJob?.cancel()
    }

    fun fetchMealDetailsIfPossibleRebindToViewMvcOtherwise() {
        if (!viewModel.isDirty && mealIdArg != Constants.INVALID_ID) {
            viewMvc.showProgressIndication()
            getMealJob = CoroutineScope(backgroundContext).launch {
                val mealDetails = getMealUseCase.getMeal(mealIdArg)
                withContext(uiContext) {
                    viewMvc.hideProgressIndication()
                    viewModel.setDefaultState(mealDetails)
                    viewMvc.bindMealDetails(mealDetails)
                }
            }
        } else {
            viewMvc.hideProgressIndication()
            viewMvc.bindMealDetails(viewModel.getState())
        }
    }

    override fun onAddNewFoodButtonClicked() {
        dialogsManager.showSelectFoodForMealScreenDialog()
    }

    override fun onDoneButtonClicked() {
        runBlocking(backgroundContext) {
            if (mealIdArg == Constants.INVALID_ID)
                insertMealUseCase.insertMeal(viewModel.getState())
            else
                updateMealUseCase.updateMeal(viewModel.getState())
        }
        screensNavigator.goBack()
    }

    override fun onNavigateUp() {
        screensNavigator.goBack()
    }

    override fun onTitleChange(newTitle: String) {
        viewModel.setTitle(newTitle)
    }

    fun setArgs(mealId: Int) {
        this.mealIdArg = mealId
    }

    override fun restoreState(state: ControllerSavedState) {
        (state as SavedState).let {
            viewModel = it.viewModel
        }
    }

    override fun getState(): ControllerSavedState {
        return SavedState(viewModel)
    }

    override fun onBackPressed(): Boolean {
        screensNavigator.goBack()
        return true
    }

    override fun onDialogEvent(event: Any, result: DialogResult?) {
        result?.let {
            if (event == SelectFoodForMealDialogEvent.ON_FOOD_CHOSEN) {
                dialogsManager.showSelectFoodQuantityDialog(result.data.getInt(FOOD_ID_RESULT))
            } else if (event == SelectFoodQuantityDialogEvent.ON_DESIRED_FOOD_SERVING_SIZE_CHOSEN) {
                val selectedFood = result.data.getSerializable(SELECTED_FOOD_RESULT) as UiFood
                val selectedFoodQuantity =
                    result.data.getSerializable(DESIRED_SERVING_SIZE_RESULT) as PhysicalQuantity
                viewModel.addMealFood(selectedFood, selectedFoodQuantity)
                viewMvc.bindMealDetails(viewModel.getState())
            }
        }
    }
}