package com.darrenfinch.mymealplanner.screens.foodform.controller

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.controllers.ControllerSavedState
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.foods.usecases.GetFoodUseCase
import com.darrenfinch.mymealplanner.foods.usecases.UpsertFoodUseCase
import com.darrenfinch.mymealplanner.physicalquantities.units.MeasurementUnit
import com.darrenfinch.mymealplanner.screens.foodform.FoodFormVm
import com.darrenfinch.mymealplanner.screens.foodform.view.FoodFormViewMvc
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class FoodFormControllerImpl(
    private var viewModel: FoodFormVm,
    private val screensNavigator: ScreensNavigator,
    private val getFoodUseCase: GetFoodUseCase,
    private val upsertFoodUseCase: UpsertFoodUseCase,
    private val backPressDispatcher: BackPressDispatcher,
    private val backgroundContext: CoroutineContext,
    private val uiContext: CoroutineContext
) : FoodFormController {

    sealed class ScreenState {
        object Loading : ScreenState()
        class HasData(val foodDetails: UiFood) : ScreenState()
    }

    data class SavedState(val viewModel: FoodFormVm, val hasLoadedFoodDetails: Boolean) :
        ControllerSavedState

    private lateinit var viewMvc: FoodFormViewMvc

    private var foodIdArg = Constants.INVALID_ID

    private var hasLoadedFoodDetails = false

    private var getFoodJob: Job? = null

    override fun bindView(viewMvc: FoodFormViewMvc) {
        this.viewMvc = viewMvc
    }

    override fun onStart() {
        viewMvc.registerListener(this)
        backPressDispatcher.registerListener(this)
    }

    override fun onStop() {
        viewMvc.unregisterListener(this)
        backPressDispatcher.unregisterListener(this)
        getFoodJob?.cancel()
    }

    override fun getFoodDetails() {
        setScreenState(ScreenState.Loading)

        if (hasLoadedFoodDetails) {
            // view model is retained on config changes, so this essentially restores the view
            setScreenState(ScreenState.HasData(viewModel.getFoodDetails()))
        } else {
            getFoodJob = CoroutineScope(backgroundContext).launch {
                val foodDetails = getFoodUseCase.getFood(foodIdArg)
                withContext(uiContext) {
                    setScreenState(ScreenState.HasData(foodDetails))
                }
            }
        }
    }

    private fun setScreenState(screenState: ScreenState) {
        when (screenState) {
            is ScreenState.Loading -> {
                viewMvc.showProgressIndication()
            }
            is ScreenState.HasData -> {
                viewMvc.hideProgressIndication()
                viewModel.bindFoodDetails(screenState.foodDetails)
                viewMvc.bindFoodDetails(screenState.foodDetails)
                hasLoadedFoodDetails = true
            }
        }
    }

    override fun onDoneButtonClicked() {
        runBlocking(backgroundContext) {
            upsertFoodUseCase.upsertFood(viewModel.getFoodDetails())
        }
        screensNavigator.navigateUp()
    }

    override fun onNavigateUp() {
        screensNavigator.navigateUp()
    }

    override fun onTitleChange(newTitle: String) {
        viewModel.setTitle(newTitle)
    }

    override fun onServingSizeQuantityChange(newServingSizeQuantity: Double) {
        viewModel.setServingSizeQuantity(newServingSizeQuantity)
    }

    override fun onServingSizeUnitChange(newServingSizeUnit: MeasurementUnit) {
        viewModel.setServingSizeUnit(newServingSizeUnit)
    }

    override fun onCaloriesChange(newCalories: Int) {
        viewModel.setCalories(newCalories)
    }

    override fun onCarbsChange(newCarbs: Int) {
        viewModel.setCarbs(newCarbs)
    }

    override fun onFatsChange(newFats: Int) {
        viewModel.setFats(newFats)
    }

    override fun onProteinsChange(newProteins: Int) {
        viewModel.setProteins(newProteins)
    }

    override fun setArgs(foodId: Int) {
        this.foodIdArg = foodId
    }

    override fun restoreState(state: ControllerSavedState) {
        (state as SavedState).let {
            viewModel = it.viewModel
            hasLoadedFoodDetails = it.hasLoadedFoodDetails
        }
    }

    override fun getState(): ControllerSavedState {
        return SavedState(viewModel, hasLoadedFoodDetails)
    }

    override fun onBackPressed(): Boolean {
        return screensNavigator.navigateUp()
    }
}