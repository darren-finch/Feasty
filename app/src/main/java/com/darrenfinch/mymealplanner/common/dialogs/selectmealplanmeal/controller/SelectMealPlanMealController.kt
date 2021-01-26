package com.darrenfinch.mymealplanner.common.dialogs.selectmealplanmeal.controller

import androidx.core.os.bundleOf
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.dialogs.DialogResult
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.dialogs.selectmealplanmeal.SelectMealPlanMealDialogEvent
import com.darrenfinch.mymealplanner.common.dialogs.selectmealplanmeal.controller.SelectMealPlanMealDialog.Companion.SELECTED_MEAL_RESULT
import com.darrenfinch.mymealplanner.common.dialogs.selectmealplanmeal.view.SelectMealPlanMealViewMvc
import com.darrenfinch.mymealplanner.common.misc.ControllerSavedState
import com.darrenfinch.mymealplanner.meals.usecases.GetAllMealsUseCase
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class SelectMealPlanMealController(
    private val getAllMealsUseCase: GetAllMealsUseCase,
    private val dialogsManager: DialogsManager,
    private val dialogsEventBus: DialogsEventBus,
    private val backgroundContext: CoroutineContext,
    private val uiContext: CoroutineContext
) : BaseController, SelectMealPlanMealViewMvc.Listener {

    class SavedState : ControllerSavedState

    private lateinit var viewMvc: SelectMealPlanMealViewMvc

    private var getAllMealsJob: Job? = null

    fun bindView(viewMvc: SelectMealPlanMealViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
        getAllMealsJob?.cancel()
    }

    fun getAllMealsAndBindToView() {
        getAllMealsJob = CoroutineScope(backgroundContext).launch {
            val allMeals = getAllMealsUseCase.getAllMeals()
            withContext(uiContext) {
                viewMvc.bindMeals(allMeals)
            }
        }
    }

    override fun onMealSelected(selectedMeal: UiMeal) {
        dialogsManager.clearDialog()
        dialogsEventBus.postEvent(SelectMealPlanMealDialogEvent.ON_MEAL_SELECTED, DialogResult(bundleOf(SELECTED_MEAL_RESULT to selectedMeal)))
    }

    override fun restoreState(state: ControllerSavedState) {}
    override fun getState(): ControllerSavedState {
        return SavedState()
    }
}