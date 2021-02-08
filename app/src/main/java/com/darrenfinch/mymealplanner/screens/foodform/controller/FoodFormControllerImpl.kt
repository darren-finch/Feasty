package com.darrenfinch.mymealplanner.screens.foodform.controller

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.misc.ControllerSavedState
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.foods.usecases.GetFoodUseCase
import com.darrenfinch.mymealplanner.foods.usecases.InsertFoodUseCase
import com.darrenfinch.mymealplanner.foods.usecases.UpdateFoodUseCase
import com.darrenfinch.mymealplanner.physicalquantities.units.MeasurementUnit
import com.darrenfinch.mymealplanner.screens.foodform.FoodFormVm
import com.darrenfinch.mymealplanner.screens.foodform.view.FoodFormViewMvc
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class FoodFormControllerImpl(
    private var viewModel: FoodFormVm,
    private val screensNavigator: ScreensNavigator,
    private val getFoodUseCase: GetFoodUseCase,
    private val insertFoodUseCase: InsertFoodUseCase,
    private val updateFoodUseCase: UpdateFoodUseCase,
    private val backPressDispatcher: BackPressDispatcher,
    private val backgroundContext: CoroutineContext,
    private val uiContext: CoroutineContext
) : FoodFormController {

    data class SavedState(val viewModel: FoodFormVm) :
        ControllerSavedState

    private lateinit var viewMvc: FoodFormViewMvc

    private var foodIdArg = Constants.INVALID_ID

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

    override fun getFoodDetailsIfPossibleAndBindToView() {
        restoreViewState()
        viewMvc.hideProgressIndication()

        if (canGetFoodDetails()) {
            viewMvc.showProgressIndication()
            getFoodJob = CoroutineScope(backgroundContext).launch {
                val foodDetails = getFoodUseCase.getFood(foodIdArg)
                withContext(uiContext) {
                    viewMvc.hideProgressIndication()
                    setInitialFoodDetails(foodDetails)
                }
            }
        }
    }

    fun restoreViewState() {
        viewMvc.bindFoodDetails(viewModel.getFoodDetails())
    }

    fun setInitialFoodDetails(foodDetails: UiFood) {
        viewMvc.bindFoodDetails(foodDetails)
        viewModel.bindInitialFoodDetails(foodDetails)
    }

    private fun canGetFoodDetails() = !viewModel.isDirty && foodIdArg != Constants.INVALID_ID

    override fun onDoneButtonClicked() {
        runBlocking(backgroundContext) {
            if (foodIdArg == Constants.INVALID_ID)
                insertFoodUseCase.insertFood(viewModel.getFoodDetails())
            else
                updateFoodUseCase.updateFood(viewModel.getFoodDetails())
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
        }
    }

    override fun getState(): ControllerSavedState {
        return SavedState(viewModel)
    }

    override fun onBackPressed(): Boolean {
        return screensNavigator.navigateUp()
    }
}