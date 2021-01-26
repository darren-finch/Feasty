package com.darrenfinch.mymealplanner.screens.foodform.controller

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.misc.ControllerSavedState
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.BackPressListener
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

class FoodFormController(
    private var viewModel: FoodFormVm,
    private val screensNavigator: ScreensNavigator,
    private val getFoodUseCase: GetFoodUseCase,
    private val insertFoodUseCase: InsertFoodUseCase,
    private val updateFoodUseCase: UpdateFoodUseCase,
    private val backPressDispatcher: BackPressDispatcher,
    private val backgroundContext: CoroutineContext,
    private val uiContext: CoroutineContext
) : BaseController, FoodFormViewMvc.Listener, BackPressListener {

    data class SavedState(val viewModel: FoodFormVm) :
        ControllerSavedState

    private lateinit var viewMvc: FoodFormViewMvc

    private var foodIdArg = Constants.INVALID_ID

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
        if (!viewModel.isDirty && foodIdArg != Constants.INVALID_ID) {
            viewMvc.showProgressIndication()
            getFoodJob = CoroutineScope(backgroundContext).launch {
                val foodDetails = getFoodUseCase.getFood(foodIdArg)
                withContext(uiContext) {
                    viewMvc.hideProgressIndication()
                    viewMvc.bindFoodDetails(foodDetails)
                    viewModel.setDefaultState(foodDetails)
                }
            }
        } else {
            viewMvc.hideProgressIndication()
            viewMvc.bindFoodDetails(viewModel.getState())
        }
    }

    override fun onDoneButtonClicked() {
        runBlocking(backgroundContext) {
            if (foodIdArg == Constants.INVALID_ID)
                insertFoodUseCase.insertFood(viewModel.getState())
            else
                updateFoodUseCase.updateFood(viewModel.getState())
        }
        screensNavigator.goBack()
    }

    override fun onNavigateUp() {
        screensNavigator.goBack()
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

    fun setArgs(foodId: Int) {
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
        screensNavigator.goBack()
        return true
    }
}