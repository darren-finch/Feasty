package com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.controller

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.controllers.BaseDialog
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.controller.SelectMealPlanMealDialog.Companion.SELECTED_MEAL
import com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.view.SelectMealPlanMealViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.GetAllMealsUseCase
import com.darrenfinch.mymealplanner.model.data.entities.Meal

class SelectMealPlanMealController(
    private val getAllMealsUseCase: GetAllMealsUseCase
) : BaseController, SelectMealPlanMealViewMvc.Listener {

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

    }

    override fun setState(state: Bundle?) {}
    override fun getState(): Bundle {
        return Bundle()
    }
}