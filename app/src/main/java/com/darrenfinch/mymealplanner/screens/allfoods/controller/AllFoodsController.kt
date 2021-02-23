package com.darrenfinch.mymealplanner.screens.allfoods.controller

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.controllers.StatefulController
import com.darrenfinch.mymealplanner.common.controllers.ControllerSavedState
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.dialogs.prompt.PromptDialogEvent
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.foods.usecases.DeleteFoodUseCase
import com.darrenfinch.mymealplanner.foods.usecases.GetAllFoodsUseCase
import com.darrenfinch.mymealplanner.screens.allfoods.AllFoodsSavableData
import com.darrenfinch.mymealplanner.screens.allfoods.view.AllFoodsViewMvc
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AllFoodsController(
    private var savableData: AllFoodsSavableData,
    private val screensNavigator: ScreensNavigator,
    private val getAllFoodsUseCase: GetAllFoodsUseCase,
    private val deleteFoodUseCase: DeleteFoodUseCase,
    private val dialogsManager: DialogsManager,
    private val dialogsEventBus: DialogsEventBus,
    private val backgroundContext: CoroutineContext,
    private val uiContext: CoroutineContext
) : StatefulController, AllFoodsViewMvc.Listener, DialogsEventBus.Listener {

    data class SavedState(val savableData: AllFoodsSavableData) : ControllerSavedState

    private lateinit var viewMvc: AllFoodsViewMvc

    private var getAllFoodsJob: Job? = null

    fun bindView(viewMvc: AllFoodsViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
        dialogsEventBus.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
        dialogsEventBus.unregisterListener(this)
        getAllFoodsJob?.cancel()
    }

    fun getAllFoodsAndBindToView() {
        viewMvc.showProgressIndication()
        getAllFoodsJob = CoroutineScope(backgroundContext).launch {
            val allFoods = getAllFoodsUseCase.getAllFoods()
            withContext(uiContext) {
                viewMvc.hideProgressIndication()
                viewMvc.bindFoods(allFoods)
            }
        }
    }

    override fun onAddNewFoodClicked() {
        screensNavigator.toFoodFormScreen(Constants.NEW_ITEM_ID)
    }

    override fun onFoodEdit(foodId: Int) {
        screensNavigator.toFoodFormScreen(foodId)
    }

    override fun onFoodDelete(foodId: Int) {
        savableData.setPendingIdOfFoodToDelete(foodId)
        dialogsManager.showDeleteFoodConfirmationDialog()
    }

    override fun restoreState(state: ControllerSavedState) {
        (state as SavedState).let {
            savableData = it.savableData
        }
    }
    override fun getState(): ControllerSavedState {
        return SavedState(savableData)
    }

    override fun onDialogEvent(event: Any) {
        if (event is PromptDialogEvent.PositiveButtonClicked) {
            runBlocking(backgroundContext) {
                deleteFoodUseCase.deleteFood(savableData.getPendingIdOfFoodToDelete())
            }
            savableData.setPendingIdOfFoodToDelete(Constants.INVALID_ID)
            getAllFoodsAndBindToView()
        }
    }
}