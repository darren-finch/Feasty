package com.darrenfinch.mymealplanner.screens.allmeals.controller

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.controllers.StatefulController
import com.darrenfinch.mymealplanner.common.controllers.ControllerSavedState
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.dialogs.prompt.PromptDialogEvent
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.meals.usecases.DeleteMealUseCase
import com.darrenfinch.mymealplanner.meals.usecases.GetAllMealsUseCase
import com.darrenfinch.mymealplanner.screens.allmeals.AllMealsSavableData
import com.darrenfinch.mymealplanner.screens.allmeals.view.AllMealsViewMvc
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AllMealsController(
    private var savableData: AllMealsSavableData,
    private val screensNavigator: ScreensNavigator,
    private val getAllMealsUseCase: GetAllMealsUseCase,
    private val deleteMealUseCase: DeleteMealUseCase,
    private val dialogsManager: DialogsManager,
    private val dialogsEventBus: DialogsEventBus,
    private val backgroundContext: CoroutineContext,
    private val uiContext: CoroutineContext
) : StatefulController, AllMealsViewMvc.Listener, DialogsEventBus.Listener {

    data class SavedState(val savableData: AllMealsSavableData) : ControllerSavedState

    private lateinit var viewMvc: AllMealsViewMvc

    private var getAllMealsJob: Job? = null

    fun bindView(viewMvc: AllMealsViewMvc) {
        this.viewMvc = viewMvc
    }

    fun getAllMealsAndBindToView() {
        viewMvc.showProgressIndication()
        getAllMealsJob = CoroutineScope(backgroundContext).launch {
            val allMeals = getAllMealsUseCase.getAllMeals()
            withContext(uiContext) {
                viewMvc.hideProgressIndication()
                viewMvc.bindMeals(allMeals)
            }
        }
    }

    fun onStart() {
        viewMvc.registerListener(this)
        dialogsEventBus.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
        dialogsEventBus.unregisterListener(this)
        getAllMealsJob?.cancel()
    }

    override fun onAddNewMealClicked() {
        screensNavigator.toMealFormScreen(Constants.NEW_ITEM_ID)
    }

    override fun onMealEdit(mealId: Int) {
        screensNavigator.toMealFormScreen(mealId)
    }

    override fun onMealDelete(mealId: Int) {
        savableData.setPendingIdForMealToDelete(mealId)
        dialogsManager.showDeleteMealConfirmationDialog()
    }

    override fun restoreState(state: ControllerSavedState) {
        (state as SavedState).let {
            savableData = state.savableData
        }
    }
    override fun getState(): ControllerSavedState {
        return SavedState(savableData)
    }

    override fun onDialogEvent(event: Any) {
       if (event is PromptDialogEvent.PositiveButtonClicked) {
           runBlocking(backgroundContext) {
               deleteMealUseCase.deleteMeal(savableData.getPendingIdForMealToDelete())
           }
           savableData.setPendingIdForMealToDelete(Constants.INVALID_ID)
           getAllMealsAndBindToView()
       }
    }
}
