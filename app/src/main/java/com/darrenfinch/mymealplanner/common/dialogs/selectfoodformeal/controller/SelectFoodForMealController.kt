package com.darrenfinch.mymealplanner.common.dialogs.selectfoodformeal.controller

import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.dialogs.DialogResult
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodformeal.SelectFoodForMealDialogEvent
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodformeal.view.SelectFoodForMealViewMvc
import com.darrenfinch.mymealplanner.foods.usecases.GetAllFoodsUseCase
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood

class SelectFoodForMealController(
    private val getAllFoodsUseCase: GetAllFoodsUseCase,
    private val dialogsManager: DialogsManager,
    private val dialogsEventBus: DialogsEventBus
) : BaseController, SelectFoodForMealViewMvc.Listener {

    class SavedState : BaseController.BaseSavedState

    private lateinit var viewMvc: SelectFoodForMealViewMvc

    fun bindView(viewMvc: SelectFoodForMealViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    fun fetchAllFoods(viewLifecycleOwner: LifecycleOwner) {
        getAllFoodsUseCase.fetchAllFoods().observe(viewLifecycleOwner, Observer {
            viewMvc.bindFoods(it)
        })
    }

    override fun restoreState(state: BaseController.BaseSavedState) {}
    override fun getState(): BaseController.BaseSavedState {
        return SavedState()
    }

    override fun onFoodChosen(food: UiFood) {
        dialogsManager.clearDialog()
        dialogsEventBus.postEvent(
            SelectFoodForMealDialogEvent.ON_FOOD_CHOSEN, DialogResult(
                bundleOf(
                    SelectFoodForMealDialog.FOOD_ID_RESULT to food.id)
            )
        )
    }
}