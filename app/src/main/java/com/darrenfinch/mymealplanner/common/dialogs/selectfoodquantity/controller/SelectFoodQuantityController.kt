package com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.controller

import androidx.core.os.bundleOf
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.dialogs.DialogResult
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.SelectFoodQuantityDialogEvent
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.controller.SelectFoodQuantityDialog.Companion.SELECTED_FOOD_QUANTITY_RESULT
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.controller.SelectFoodQuantityDialog.Companion.SELECTED_FOOD_RESULT
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.view.SelectFoodQuantityViewMvc
import com.darrenfinch.mymealplanner.common.constants.DefaultModels
import com.darrenfinch.mymealplanner.foods.models.domain.MacroCalculator
import com.darrenfinch.mymealplanner.foods.models.mappers.macroNutrientsToUiMacroNutrients
import com.darrenfinch.mymealplanner.foods.models.mappers.uiMacroNutrientsToMacroNutrients
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.foods.usecases.GetFoodUseCase
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class SelectFoodQuantityController(
    private val getFoodUseCase: GetFoodUseCase,
    private val dialogsManager: DialogsManager,
    private val dialogsEventBus: DialogsEventBus,
    private val backgroundContext: CoroutineContext,
    private val uiContext: CoroutineContext
) : BaseController, SelectFoodQuantityViewMvc.Listener {

    data class SavedState(val food: UiFood) : BaseController.BaseSavedState

    private var foodIdArg = -1

    private var foodState = DefaultModels.defaultUiFood

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
        viewMvc.bindFood(foodState)

        val hasLoadedFoodDetails = foodState != DefaultModels.defaultUiFood
        if (!hasLoadedFoodDetails) {
            getFoodJob = CoroutineScope(backgroundContext).launch {
                foodState = getFoodUseCase.getFood(foodIdArg)
                withContext(uiContext) {
                    viewMvc.bindFood(foodState)
                }
            }
        }
    }

    fun setArgs(foodId: Int) {
        this.foodIdArg = foodId
    }

    override fun restoreState(state: BaseController.BaseSavedState) {
        (state as SavedState).let {
            foodState = state.food
        }
    }

    override fun getState(): BaseController.BaseSavedState {
        return SavedState(viewMvc.getFoodData())
    }

    override fun onFoodServingSizeChosen(
        selectedFood: UiFood,
        selectedFoodQuantity: PhysicalQuantity
    ) {
        dialogsManager.clearDialog()
        dialogsEventBus.postEvent(
            SelectFoodQuantityDialogEvent.ON_FOOD_QUANTITY_CHOSEN,
            DialogResult(
                bundleOf(
                    SELECTED_FOOD_RESULT to selectedFood,
                    SELECTED_FOOD_QUANTITY_RESULT to selectedFoodQuantity
                )
            )
        )
    }

    override fun onServingSizeChange(newServingSize: PhysicalQuantity) {
        val updatedFood = foodState.copy(
            servingSize = newServingSize,
            macroNutrients = macroNutrientsToUiMacroNutrients(
                MacroCalculator.baseMacrosOnNewServingSize(
                    uiMacroNutrientsToMacroNutrients(foodState.macroNutrients),
                    foodState.servingSize,
                    newServingSize
                )
            )
        )
        viewMvc.bindFood(updatedFood)
    }
}
