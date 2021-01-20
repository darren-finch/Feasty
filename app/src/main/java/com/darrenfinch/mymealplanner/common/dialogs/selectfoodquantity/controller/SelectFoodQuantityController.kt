package com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.controller

import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.dialogs.DialogResult
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.utils.DefaultModels
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.SelectFoodQuantityDialogEvent
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.controller.SelectFoodQuantityDialog.Companion.SELECTED_FOOD_QUANTITY_RESULT
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.controller.SelectFoodQuantityDialog.Companion.SELECTED_FOOD_RESULT
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.view.SelectFoodQuantityViewMvc
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.foods.usecases.GetFoodUseCase
import com.darrenfinch.mymealplanner.foods.models.Food

class SelectFoodQuantityController(
    private val getFoodUseCase: GetFoodUseCase,
    private val dialogsManager: DialogsManager,
    private val dialogsEventBus: DialogsEventBus
) : BaseController, SelectFoodQuantityViewMvc.Listener {

    data class SavedState(val food: Food) : BaseController.BaseSavedState

    private var foodIdArg = -1

    private var foodState = DefaultModels.defaultFood

    private lateinit var viewMvc: SelectFoodQuantityViewMvc

    fun bindView(viewMvc: SelectFoodQuantityViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    fun fetchFood(viewLifecycleOwner: LifecycleOwner) {
        viewMvc.bindFood(foodState)

        val hasLoadedFoodDetails = foodState != DefaultModels.defaultFood
        if (!hasLoadedFoodDetails) {
            getFoodUseCase.fetchFood(foodIdArg).observe(viewLifecycleOwner, Observer {
                foodState = it
                viewMvc.bindFood(it)
            })
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
        selectedFood: Food,
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
}
