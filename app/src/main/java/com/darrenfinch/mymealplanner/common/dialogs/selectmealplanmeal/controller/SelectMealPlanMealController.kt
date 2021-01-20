package com.darrenfinch.mymealplanner.common.dialogs.selectmealplanmeal.controller

import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.dialogs.DialogResult
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.dialogs.selectmealplanmeal.SelectMealPlanMealDialogEvent
import com.darrenfinch.mymealplanner.common.dialogs.selectmealplanmeal.controller.SelectMealPlanMealDialog.Companion.SELECTED_MEAL_RESULT
import com.darrenfinch.mymealplanner.common.dialogs.selectmealplanmeal.view.SelectMealPlanMealViewMvc
import com.darrenfinch.mymealplanner.meals.usecases.GetAllMealsUseCase
import com.darrenfinch.mymealplanner.meals.models.Meal

class SelectMealPlanMealController(
    private val getAllMealsUseCase: GetAllMealsUseCase,
    private val dialogsManager: DialogsManager,
    private val dialogsEventBus: DialogsEventBus
) : BaseController, SelectMealPlanMealViewMvc.Listener {

    class SavedState : BaseController.BaseSavedState

    private lateinit var viewMvc: SelectMealPlanMealViewMvc

    fun bindView(viewMvc: SelectMealPlanMealViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    fun fetchMeals(lifecycleOwner: LifecycleOwner) {
        getAllMealsUseCase.fetchAllMeals().observe(lifecycleOwner, Observer {
            viewMvc.bindMeals(it)
        })
    }

    override fun onMealSelected(selectedMeal: Meal) {
        dialogsManager.clearDialog()
        dialogsEventBus.postEvent(SelectMealPlanMealDialogEvent.ON_MEAL_SELECTED, DialogResult(bundleOf(SELECTED_MEAL_RESULT to selectedMeal)))
    }

    override fun restoreState(state: BaseController.BaseSavedState) {}
    override fun getState(): BaseController.BaseSavedState {
        return SavedState()
    }
}