package com.darrenfinch.mymealplanner.common.dialogs.editmealfood.controller

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.EditMealFoodDialogEvent
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.EditMealFoodVm
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.view.EditMealFoodViewMvc
import com.darrenfinch.mymealplanner.common.controllers.ControllerSavedState
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood

class EditMealFoodController(
    private var viewModel: EditMealFoodVm,
    private val dialogsManager: DialogsManager,
    private val dialogsEventBus: DialogsEventBus,
) : BaseController, EditMealFoodViewMvc.Listener {

    data class SavedState(val viewModel: EditMealFoodVm, val hasLoadedMealFoodDetails: Boolean, val indexArgState: Int) :
        ControllerSavedState

    private lateinit var viewMvc: EditMealFoodViewMvc

    private var indexArgState = Constants.INVALID_INDEX

    private var hasLoadedMealFoodDetails = false

    fun bindView(viewMvc: EditMealFoodViewMvc) {
        this.viewMvc = viewMvc
    }

    fun bindViewStateToView() {
        viewMvc.bindMealFoodTitle(viewModel.getMealFoodTitle())
        viewMvc.bindMealFoodMacros(viewModel.getUpdatedMealFoodMacros())
        viewMvc.bindMealFoodDesiredServingSize(viewModel.getDesiredMealFoodServingSize())
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    override fun restoreState(state: ControllerSavedState) {
        (state as SavedState).let {
            viewModel = it.viewModel
            hasLoadedMealFoodDetails = it.hasLoadedMealFoodDetails
            indexArgState = it.indexArgState
        }
    }

    override fun getState(): ControllerSavedState {
        return SavedState(viewModel, hasLoadedMealFoodDetails, indexArgState)
    }

    fun setArgs(mealFood: UiMealFood, index: Int) {
        indexArgState = index
        viewModel.bindMealFoodDetails(mealFood)
    }

    override fun onPositiveButtonClicked() {
        dialogsManager.clearDialog()
        dialogsEventBus.postEvent(
            EditMealFoodDialogEvent.OnPositiveButtonClicked(viewModel.getMealFoodDetails(), indexArgState)
        )
    }

    override fun onMealFoodServingSizeQuantityChange(quantity: Double) {
        viewModel.setDesiredServingSizeQuantity(quantity)
        viewMvc.bindMealFoodMacros(viewModel.getUpdatedMealFoodMacros())
    }
}