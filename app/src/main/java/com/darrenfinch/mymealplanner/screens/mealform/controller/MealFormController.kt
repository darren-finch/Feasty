package com.darrenfinch.mymealplanner.screens.mealform.controller

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.constants.DefaultModels
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.dialogs.DialogResult
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodformeal.SelectFoodForMealDialogEvent
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.SelectFoodQuantityDialogEvent
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.controller.SelectFoodQuantityDialog.Companion.FOOD_ID_RESULT
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.controller.SelectFoodQuantityDialog.Companion.SELECTED_FOOD_QUANTITY_RESULT
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.controller.SelectFoodQuantityDialog.Companion.SELECTED_FOOD_RESULT
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.BackPressListener
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood
import com.darrenfinch.mymealplanner.meals.usecases.GetMealUseCase
import com.darrenfinch.mymealplanner.meals.usecases.InsertMealUseCase
import com.darrenfinch.mymealplanner.meals.usecases.UpdateMealUseCase
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.screens.mealform.view.MealFormViewMvc
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MealFormController(
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

    data class SavedState(val hasLoadedMealDetails: Boolean, val mealDetails: UiMeal) :
        BaseController.BaseSavedState

    private var mealIdArg = Constants.INVALID_ID

    private var hasLoadedMealDetailsState = false
    private var mealDetailsState: UiMeal = DefaultModels.defaultUiMeal

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
        val shouldFetchMealDetails = mealIdArg != Constants.INVALID_ID && !hasLoadedMealDetailsState
        if (shouldFetchMealDetails) {
            viewMvc.showProgressIndication()
            getMealJob = CoroutineScope(backgroundContext).launch {
                mealDetailsState = getMealUseCase.getMeal(mealIdArg)
                hasLoadedMealDetailsState = true
                withContext(uiContext) {
                    viewMvc.hideProgressIndication()
                    viewMvc.bindMealDetails(mealDetailsState)
                }
            }
        } else {
            viewMvc.hideProgressIndication()
            viewMvc.bindMealDetails(mealDetailsState)
        }
    }

    override fun onAddNewFoodButtonClicked() {
        dialogsManager.showSelectFoodForMealScreenDialog()
    }

    override fun onDoneButtonClicked(editedMealDetails: UiMeal) {
        runBlocking(backgroundContext) {
            if (mealIdArg == Constants.INVALID_ID)
                insertMealUseCase.insertMeal(editedMealDetails)
            else
                updateMealUseCase.updateMeal(editedMealDetails)
        }
        screensNavigator.goBack()
    }

    fun setArgs(mealId: Int) {
        this.mealIdArg = mealId
    }

    override fun restoreState(state: BaseController.BaseSavedState) {
        (state as SavedState).let {
            hasLoadedMealDetailsState = it.hasLoadedMealDetails
            mealDetailsState = it.mealDetails
        }
    }

    override fun getState(): BaseController.BaseSavedState {
        return SavedState(hasLoadedMealDetailsState, viewMvc.getMealDetails())
    }

    override fun onBackPressed(): Boolean {
        screensNavigator.goBack()
        return true
    }

    override fun onDialogEvent(event: Any, result: DialogResult?) {
        mealDetailsState = viewMvc.getMealDetails()
        result?.let {
            if (event == SelectFoodForMealDialogEvent.ON_FOOD_CHOSEN) {
                dialogsManager.showSelectFoodQuantityDialog(result.data.getInt(FOOD_ID_RESULT))
            }
            else if (event == SelectFoodQuantityDialogEvent.ON_FOOD_QUANTITY_CHOSEN) {
                val selectedFood = result.data.getSerializable(SELECTED_FOOD_RESULT) as UiFood
                val selectedFoodQuantity =
                    result.data.getSerializable(SELECTED_FOOD_QUANTITY_RESULT) as PhysicalQuantity
                val mealFoodFromSelectedFood = UiMealFood(
                    id = Constants.INVALID_ID,
                    foodId = selectedFood.id,
                    mealId = mealDetailsState.id,
                    title = selectedFood.title,
                    macroNutrients = selectedFood.macroNutrients,
                    desiredServingSize = selectedFoodQuantity
                )
                mealDetailsState =
                    mealDetailsState.copy(foods = mealDetailsState.foods + mealFoodFromSelectedFood)
                viewMvc.bindMealDetails(mealDetailsState)
            }
        }
    }
}