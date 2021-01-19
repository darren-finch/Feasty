package com.darrenfinch.mymealplanner.domain.mealform.controller

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.misc.Constants
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.BackPressListener
import com.darrenfinch.mymealplanner.common.navigation.DialogsManager
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.common.utils.DefaultModels
import com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.controller.SelectFoodForMealDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectFoodQuantityDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectFoodQuantityDialog.Companion.FOOD_ID_RESULT
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectFoodQuantityDialog.Companion.SELECTED_FOOD_QUANTITY_RESULT
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealfoodquantity.controller.SelectFoodQuantityDialog.Companion.SELECTED_FOOD_RESULT
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
    private val backPressDispatcher: BackPressDispatcher
) : BaseController, MealFormViewMvc.Listener, BackPressListener {

    data class SavedState(val hasLoadedMealDetails: Boolean, val mealDetails: Meal) : BaseController.BaseSavedState

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
        backPressDispatcher.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
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
        if(updatingMeal) {
            updateMealUseCase.updateMeal(editedMealDetails)
        }
        else {
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

    fun setDialogResults(tag: String, results: Bundle) {
        if(tag == SelectFoodForMealDialog.TAG) {
            dialogsManager.showSelectFoodQuantityDialog(results.getInt(FOOD_ID_RESULT))
        }
        else if (tag == SelectFoodQuantityDialog.TAG) {
            val selectedFood = results.getSerializable(SELECTED_FOOD_RESULT) as Food
            val selectedFoodQuantity = results.getSerializable(SELECTED_FOOD_QUANTITY_RESULT) as PhysicalQuantity
            val mealFoodFromSelectedFood = MealFood(
                id = Constants.VALID_ID,
                foodId = selectedFood.id,
                mealId = mealDetailsState.id,
                title = selectedFood.title,
                macroNutrients = selectedFood.macroNutrients,
                desiredServingSize = selectedFoodQuantity
            )
            mealDetailsState = mealDetailsState.copy(foods = mealDetailsState.foods + mealFoodFromSelectedFood)
            viewMvc.bindMealDetails(mealDetailsState)
        }
    }
}