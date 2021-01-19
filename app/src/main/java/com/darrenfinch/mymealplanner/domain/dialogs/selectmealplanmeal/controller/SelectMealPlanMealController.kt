package com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.controller

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.navigation.DialogsManager
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.view.SelectMealPlanMealViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.GetAllMealsUseCase
import com.darrenfinch.mymealplanner.model.data.entities.Meal

class SelectMealPlanMealController(
    private val getAllMealsUseCase: GetAllMealsUseCase,
    private val dialogsManager: DialogsManager
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

    override fun onMealSelected(meal: Meal) {
        dialogsManager.clearDialog()
    }

    override fun restoreState(state: BaseController.BaseSavedState) {}
    override fun getState(): BaseController.BaseSavedState {
        return SavedState()
    }
}