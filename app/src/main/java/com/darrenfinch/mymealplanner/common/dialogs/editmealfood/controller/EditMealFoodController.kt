package com.darrenfinch.mymealplanner.common.dialogs.editmealfood.controller

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.EditMealFoodDialogEvent
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.EditMealFoodDialogData
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.view.EditMealFoodViewMvc
import com.darrenfinch.mymealplanner.common.controllers.ControllerSavedState
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood

class EditMealFoodController(
    private var screenData: EditMealFoodDialogData,
    private val dialogsManager: DialogsManager,
    private val dialogsEventBus: DialogsEventBus,
) : BaseController, EditMealFoodViewMvc.Listener {

    data class SavedState(val screenData: EditMealFoodDialogData, val hasLoadedMealFoodDetails: Boolean, val indexArgState: Int) :
        ControllerSavedState

    private lateinit var viewMvc: EditMealFoodViewMvc

    // This variable is an argument that is also saved across config changes, so I'm keeping it here in the controller for now.
    private var indexArgState = Constants.INVALID_INDEX

    private var hasLoadedMealFoodDetails = false

    fun bindView(viewMvc: EditMealFoodViewMvc) {
        this.viewMvc = viewMvc
    }

    fun bindViewStateToView() {
        viewMvc.bindMealFoodTitle(screenData.getTitle())
        viewMvc.bindMealFoodMacros(screenData.getMacrosBasedOnDesiredServingSize())
        viewMvc.bindMealFoodDesiredServingSize(screenData.getDesiredServingSize())
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    override fun restoreState(state: ControllerSavedState) {
        (state as SavedState).let {
            screenData = it.screenData
            hasLoadedMealFoodDetails = it.hasLoadedMealFoodDetails
            indexArgState = it.indexArgState
        }
    }

    override fun getState(): ControllerSavedState {
        return SavedState(screenData, hasLoadedMealFoodDetails, indexArgState)
    }

    fun setArgs(mealFood: UiMealFood, index: Int) {
        indexArgState = index
        screenData.bindMealFoodDetails(mealFood)
    }

    override fun onPositiveButtonClicked() {
        dialogsManager.clearDialog()
        dialogsEventBus.postEvent(
            EditMealFoodDialogEvent.OnPositiveButtonClicked(screenData.getMealFoodDetails(), indexArgState)
        )
    }

    override fun onMealFoodServingSizeQuantityChange(quantity: Double) {
        screenData.setDesiredServingSizeQuantity(quantity)
        viewMvc.bindMealFoodMacros(screenData.getMacrosBasedOnDesiredServingSize())
    }
}