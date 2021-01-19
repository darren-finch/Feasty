package com.darrenfinch.mymealplanner.domain.mealform.controller

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.dialogs.DialogResult
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.misc.Constants
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.BackPressListener
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.common.utils.DefaultModels
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.SelectFoodForMealDialogEvent
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodquantity.SelectFoodQuantityDialogEvent
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodquantity.controller.SelectFoodQuantityDialog.Companion.FOOD_ID_RESULT
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodquantity.controller.SelectFoodQuantityDialog.Companion.SELECTED_FOOD_QUANTITY_RESULT
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodquantity.controller.SelectFoodQuantityDialog.Companion.SELECTED_FOOD_RESULT
import com.darrenfinch.mymealplanner.domain.mealform.view.MealFormViewMvc
import com.darrenfinch.mymealplanner.domain.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.domain.usecases.GetMealUseCase
import com.darrenfinch.mymealplanner.domain.usecases.InsertMealUseCase
import com.darrenfinch.mymealplanner.domain.usecases.UpdateMealUseCase
import com.darrenfinch.mymealplanner.model.data.entities.Food
import com.darrenfinch.mymealplanner.model.data.entities.Meal
import com.darrenfinch.mymealplanner.model.data.entities.MealFood

class MealFormController(
    private val insertMealUseCase: InsertMealUseCase,
    private val updateMealUseCase: UpdateMealUseCase,
    private val getMealUseCase: GetMealUseCase,
    private val screensNavigator: ScreensNavigator,
    private val dialogsManager: DialogsManager,
    private val dialogsEventBus: DialogsEventBus,
    private val backPressDispatcher: BackPressDispatcher
) : BaseController, MealFormViewMvc.Listener, BackPressListener, DialogsEventBus.Listener {

    data class SavedState(val hasLoadedMealDetails: Boolean, val mealDetails: Meal) :
        BaseController.BaseSavedState

    private var mealIdArg = Constants.INVALID_ID

    private var hasLoadedMealDetailsState = false
    private var mealDetailsState: Meal = DefaultModels.defaultMeal

    private lateinit var viewMvc: MealFormViewMvc

    private val updatingMeal: Boolean
        get() = mealIdArg != Constants.INVALID_ID

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
    }

    fun fetchMealDetailsIfPossibleRebindToViewMvcOtherwise(viewLifecycleOwner: LifecycleOwner) {
        val shouldFetchMealDetails = updatingMeal && !hasLoadedMealDetailsState
        if (shouldFetchMealDetails) {
            getMealUseCase.getMeal(mealIdArg).observe(viewLifecycleOwner, Observer {
                mealDetailsState = it
                hasLoadedMealDetailsState = true
                viewMvc.bindMealDetails(it)
            })
        } else {
            viewMvc.bindMealDetails(mealDetailsState)
        }
    }

    override fun onAddNewFoodButtonClicked() {
        dialogsManager.showSelectFoodForMealScreenDialog()
    }

    override fun onDoneButtonClicked(editedMealDetails: Meal) {
        if (updatingMeal) {
            updateMealUseCase.updateMeal(editedMealDetails)
        } else {
            insertMealUseCase.insertMeal(editedMealDetails)
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
        return SavedState(hasLoadedMealDetailsState, mealDetailsState)
    }

    override fun onBackPressed(): Boolean {
        screensNavigator.goBack()
        return true
    }

    override fun onDialogEvent(event: Any, result: DialogResult?) {
        result?.let {
            if (event == SelectFoodForMealDialogEvent.ON_FOOD_CHOSEN) {
                dialogsManager.showSelectFoodQuantityDialog(result.data.getInt(FOOD_ID_RESULT))
            }
            else if (event == SelectFoodQuantityDialogEvent.ON_FOOD_QUANTITY_CHOSEN) {
                val selectedFood = result.data.getSerializable(SELECTED_FOOD_RESULT) as Food
                val selectedFoodQuantity =
                    result.data.getSerializable(SELECTED_FOOD_QUANTITY_RESULT) as PhysicalQuantity
                val mealFoodFromSelectedFood = MealFood(
                    id = Constants.VALID_ID,
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