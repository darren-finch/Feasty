package com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.controller

import androidx.core.os.bundleOf
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.dialogs.DialogResult
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.SelectFoodQuantityDialogEvent
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.SelectFoodQuantityVm
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.controller.SelectFoodQuantityDialog.Companion.SELECTED_SERVING_SIZE_RESULT
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.controller.SelectFoodQuantityDialog.Companion.SELECTED_FOOD_RESULT
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.view.SelectFoodQuantityViewMvc
import com.darrenfinch.mymealplanner.common.misc.ControllerSavedState
import com.darrenfinch.mymealplanner.foods.usecases.GetFoodUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class SelectFoodQuantityController(
    private var viewModel: SelectFoodQuantityVm,
    private val getFoodUseCase: GetFoodUseCase,
    private val dialogsManager: DialogsManager,
    private val dialogsEventBus: DialogsEventBus,
    private val backgroundContext: CoroutineContext,
    private val uiContext: CoroutineContext
) : BaseController, SelectFoodQuantityViewMvc.Listener {

    data class SavedState(val viewModel: SelectFoodQuantityVm) :
        ControllerSavedState

    private var foodIdArg = -1

    private lateinit var viewMvc: SelectFoodQuantityViewMvc

    private var getFoodJob: Job? = null

    fun bindView(viewMvc: SelectFoodQuantityViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
        getFoodJob?.cancel()
    }

    fun getFoodAndBindToView() {
        viewMvc.bindFood(viewModel.getSelectedFood())

        if (!viewModel.isDirty) {
            getFoodJob = CoroutineScope(backgroundContext).launch {
                val food = getFoodUseCase.getFood(foodIdArg)
                withContext(uiContext) {
                    viewModel.setSelectedFood(food)
                    viewMvc.bindFood(food)
                }
            }
        }
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

    override fun onPositiveButtonClicked() {
        dialogsManager.clearDialog()
        dialogsEventBus.postEvent(
            SelectFoodQuantityDialogEvent.POSITIVE,
            DialogResult(
                bundleOf(
                    SELECTED_FOOD_RESULT to viewModel.getSelectedFood(),
                    SELECTED_SERVING_SIZE_RESULT to viewModel.getSelectedServingSize(),
                )
            )
        )
    }

    override fun onFoodQuantityChange(newQuantity: Double) {
        viewModel.setSelectedFoodQuantity(newQuantity)
        viewMvc.bindFood(viewModel.getSelectedFood())
    }
}
