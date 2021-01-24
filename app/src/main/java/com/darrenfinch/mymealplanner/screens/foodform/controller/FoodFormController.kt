package com.darrenfinch.mymealplanner.screens.foodform.controller

import android.util.Log
import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.constants.DefaultModels
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.messages.ToastsHelper
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.BackPressListener
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.foods.usecases.GetFoodUseCase
import com.darrenfinch.mymealplanner.foods.usecases.InsertFoodUseCase
import com.darrenfinch.mymealplanner.foods.usecases.UpdateFoodUseCase
import com.darrenfinch.mymealplanner.screens.foodform.view.FoodFormViewMvc
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class FoodFormController(
    private val screensNavigator: ScreensNavigator,
    private val getFoodUseCase: GetFoodUseCase,
    private val insertFoodUseCase: InsertFoodUseCase,
    private val updateFoodUseCase: UpdateFoodUseCase,
    private val backPressDispatcher: BackPressDispatcher,
    private val backgroundContext: CoroutineContext,
    private val uiContext: CoroutineContext
) : BaseController, FoodFormViewMvc.Listener, BackPressListener {

    data class SavedState(val hasLoadedFoodDetails: Boolean, val foodDetails: UiFood) : BaseController.BaseSavedState

    private lateinit var viewMvc: FoodFormViewMvc

    private var foodIdArg = Constants.INVALID_ID

    private var foodDetailsState: UiFood = DefaultModels.defaultUiFood
    private var hasLoadedFoodDetailsState = false

    private var getFoodJob: Job? = null

    fun bindView(viewMvc: FoodFormViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
        backPressDispatcher.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
        backPressDispatcher.unregisterListener(this)
        getFoodJob?.cancel()
    }

    fun getFoodDetailsIfPossibleAndBindToView() {
        val shouldFetchFoodDetails = foodIdArg != Constants.INVALID_ID && !hasLoadedFoodDetailsState
        if (shouldFetchFoodDetails) {
            viewMvc.showProgressIndication()
            getFoodJob = CoroutineScope(backgroundContext).launch {
                foodDetailsState = getFoodUseCase.getFood(foodIdArg)
                hasLoadedFoodDetailsState = true
                withContext(uiContext) {
                    viewMvc.hideProgressIndication()
                    viewMvc.bindFoodDetails(foodDetailsState)
                }
            }
        } else {
            viewMvc.hideProgressIndication()
            viewMvc.bindFoodDetails(foodDetailsState)
        }
    }

    override fun onDoneButtonClicked(editedFoodDetails: UiFood) {
        runBlocking(backgroundContext) {
            if (foodIdArg == Constants.INVALID_ID)
                insertFoodUseCase.insertFood(editedFoodDetails)
            else
                updateFoodUseCase.updateFood(editedFoodDetails)
        }
        screensNavigator.goBack()
    }

    override fun onNavigateUp() {
        screensNavigator.goBack()
    }

    fun setArgs(foodId: Int) {
        this.foodIdArg = foodId
    }

    override fun restoreState(state: BaseController.BaseSavedState) {
        Log.d("AllFoodsController", "restoreState()")
        (state as SavedState).let {
            hasLoadedFoodDetailsState = it.hasLoadedFoodDetails
            foodDetailsState = it.foodDetails
        }
    }

    override fun getState(): BaseController.BaseSavedState {
        return SavedState(hasLoadedFoodDetailsState, viewMvc.getFoodDetails())
    }

    override fun onBackPressed(): Boolean {
        screensNavigator.goBack()
        return true
    }
}