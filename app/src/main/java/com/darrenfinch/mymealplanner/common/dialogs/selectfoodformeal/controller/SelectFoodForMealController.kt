package com.darrenfinch.mymealplanner.common.dialogs.selectfoodformeal.controller

import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.dialogs.DialogResult
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodformeal.SelectFoodForMealDialogEvent
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodformeal.controller.SelectFoodForMealDialog.Companion.FOOD_ID_RESULT
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodformeal.view.SelectFoodForMealViewMvc
import com.darrenfinch.mymealplanner.common.misc.ControllerSavedState
import com.darrenfinch.mymealplanner.foods.usecases.GetAllFoodsUseCase
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class SelectFoodForMealController(
    private val getAllFoodsUseCase: GetAllFoodsUseCase,
    private val dialogsManager: DialogsManager,
    private val dialogsEventBus: DialogsEventBus,
    private val backgroundContext: CoroutineContext,
    private val uiContext: CoroutineContext
) : BaseController, SelectFoodForMealViewMvc.Listener {

    class SavedState : ControllerSavedState

    private lateinit var viewMvc: SelectFoodForMealViewMvc

    private var getAllFoodsJob: Job? = null

    fun bindView(viewMvc: SelectFoodForMealViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
        getAllFoodsJob?.cancel()
    }

    fun getAllFoodsAndBindToView() {
        getAllFoodsJob = CoroutineScope(backgroundContext).launch {
            val allFoods = getAllFoodsUseCase.getAllFoods()
            withContext(uiContext) {
                viewMvc.bindFoods(allFoods)
            }
        }
    }

    override fun restoreState(state: ControllerSavedState) {}
    override fun getState(): ControllerSavedState {
        return SavedState()
    }

    override fun onFoodChosen(foodId: Int) {
        dialogsManager.clearDialog()
        dialogsEventBus.postEvent(
            SelectFoodForMealDialogEvent.ON_FOOD_CHOSEN, DialogResult().apply {
                putInt(FOOD_ID_RESULT, foodId)
            }
        )
    }
}